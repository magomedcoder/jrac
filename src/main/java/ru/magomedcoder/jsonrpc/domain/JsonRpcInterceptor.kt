package ru.magomedcoder.jsonrpc.domain

import ru.magomedcoder.jsonrpc.domain.protocol.JsonRpcRequest
import ru.magomedcoder.jsonrpc.domain.protocol.JsonRpcResponse

interface JsonRpcInterceptor {

    fun intercept(chain: Chain): JsonRpcResponse

    interface Chain {
        fun proceed(request: JsonRpcRequest): JsonRpcResponse

        fun request(): JsonRpcRequest
    }
}