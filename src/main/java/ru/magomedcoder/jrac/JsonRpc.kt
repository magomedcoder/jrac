package ru.magomedcoder.jrac

import ru.magomedcoder.jrac.domain.JsonRpc
import ru.magomedcoder.jrac.domain.JsonRpcClient
import ru.magomedcoder.jrac.domain.JsonRpcException
import ru.magomedcoder.jrac.domain.JsonRpcInterceptor
import ru.magomedcoder.jrac.domain.parser.ResultParser
import ru.magomedcoder.jrac.domain.protocol.JsonRpcRequest
import java.lang.reflect.*
import java.util.concurrent.atomic.AtomicLong

val requestId = AtomicLong(0)

// Создаем JSON-RPC сервис с использованием указанных параметров.
fun <T> createJsonRpcService(
    service: Class<T>,
    client: JsonRpcClient,
    resultParser: ResultParser,
    interceptors: List<JsonRpcInterceptor> = listOf(),
    logger: (String) -> Unit = {}
): T {
    val classLoader = service.classLoader
    val interfaces = arrayOf<Class<*>>(service)
    val invocationHandler =
        createInvocationHandler(service, client, resultParser, interceptors, logger)
    @Suppress("UNCHECKED_CAST")
    return Proxy.newProxyInstance(classLoader, interfaces, invocationHandler) as T
}

// Получаем аннотированные параметры метода JSON-RPC.
private fun Method.jsonRpcParameters(args: Array<Any?>?, service: Class<*>): Map<String, Any?> {
    return parameterAnnotations
        .map { annotation -> annotation?.firstOrNull { JsonRpc::class.java.isInstance(it) } }
        .mapIndexed { index, annotation ->
            when (annotation) {
                is JsonRpc -> annotation.value
                else -> throw IllegalStateException(
                    "Аргумент #$index метода ${service.name}#$name() должен быть аннотирован как @${JsonRpc::class.java.simpleName}"
                )
            }
        }
        .mapIndexed { i, name -> name to args?.get(i) }
        .associate { it }
}

// Создаем обработчик вызова метода JSON-RPC.
private fun <T> createInvocationHandler(
    service: Class<T>,
    client: JsonRpcClient,
    resultParser: ResultParser,
    interceptors: List<JsonRpcInterceptor> = listOf(),
    logger: (String) -> Unit
): InvocationHandler {
    return object : InvocationHandler {
        override fun invoke(proxy: Any, method: Method, args: Array<Any?>?): Any {
            val methodAnnotation =
                method.getAnnotation(JsonRpc::class.java)
                    ?: throw IllegalStateException("Метод должен быть аннотирован аннотацией JsonRpc")
            val id = requestId.incrementAndGet()
            val methodName = methodAnnotation.value
            val parameters = method.jsonRpcParameters(args, service)
            val request = JsonRpcRequest(id, methodName, parameters)
            // Добавляем интерцептор, который осуществляет сетевой вызов.
            val serverCallInterceptor = ServerCallInterceptor(client)
            val finalInterceptors = interceptors.plus(serverCallInterceptor)
            val chain = RealInterceptorChain(client, finalInterceptors, request)
            val response = chain.interceptors.first().intercept(chain)
            val returnType: Type = if (method.genericReturnType is ParameterizedType) {
                method.genericReturnType
            } else {
                method.returnType
            }
            logger("JsonRPC: Парсинг $returnType")
            if (response.result != null) {
                return resultParser.parse(returnType, response.result)
            } else {
                checkNotNull(response.error)
                throw JsonRpcException(
                    response.error.message,
                    response.error.code,
                    response.error.data
                )
            }
        }
    }
}
