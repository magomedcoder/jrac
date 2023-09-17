package ru.magomedcoder.jsonrpc

import ru.magomedcoder.jsonrpc.domain.JsonRpcClient
import ru.magomedcoder.jsonrpc.domain.JsonRpcInterceptor
import ru.magomedcoder.jsonrpc.domain.protocol.JsonRpcRequest
import ru.magomedcoder.jsonrpc.domain.protocol.JsonRpcResponse

data class RealInterceptorChain(
    private val client: JsonRpcClient,
    val interceptors: List<JsonRpcInterceptor>,
    private val request: JsonRpcRequest,
    private val index: Int = 0
) : JsonRpcInterceptor.Chain {

    override fun proceed(request: JsonRpcRequest): JsonRpcResponse {
        // Вызываем следующий интерсептор в цепочке.
        // Последний в цепочке - ServerCallInterceptor.
        val nextChain = copy(index = index + 1, request = request)
        val nextInterceptor = interceptors[index]
        return nextInterceptor.intercept(nextChain)
    }

    override fun request(): JsonRpcRequest = request

    override fun toString(): String {
        return "RealInterceptorChain(index=$index, interceptors=$interceptors)"
    }
}