package ru.magomedcoder.jrac.domain

import ru.magomedcoder.jrac.domain.protocol.JsonRpcRequest
import ru.magomedcoder.jrac.domain.protocol.JsonRpcResponse

interface JsonRpcInterceptor {

    fun intercept(chain: Chain): JsonRpcResponse

    interface Chain {
        fun proceed(request: JsonRpcRequest): JsonRpcResponse

        fun request(): JsonRpcRequest
    }
}