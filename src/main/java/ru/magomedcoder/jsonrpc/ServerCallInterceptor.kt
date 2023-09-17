package ru.magomedcoder.jsonrpc

import ru.magomedcoder.jsonrpc.domain.JsonRpcClient
import ru.magomedcoder.jsonrpc.domain.JsonRpcInterceptor
import ru.magomedcoder.jsonrpc.domain.protocol.JsonRpcResponse

class ServerCallInterceptor(private val client: JsonRpcClient) : JsonRpcInterceptor {

    override fun intercept(chain: JsonRpcInterceptor.Chain): JsonRpcResponse {
        return client.call(chain.request())
    }
}