package ru.magomedcoder.jrac

import ru.magomedcoder.jrac.domain.JsonRpcClient
import ru.magomedcoder.jrac.domain.JsonRpcInterceptor
import ru.magomedcoder.jrac.domain.protocol.JsonRpcResponse

class ServerCallInterceptor(private val client: JsonRpcClient) : JsonRpcInterceptor {

    override fun intercept(chain: JsonRpcInterceptor.Chain): JsonRpcResponse {
        return client.call(chain.request())
    }
}