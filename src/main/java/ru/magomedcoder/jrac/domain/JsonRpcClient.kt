package ru.magomedcoder.jrac.domain

import ru.magomedcoder.jrac.domain.protocol.JsonRpcRequest
import ru.magomedcoder.jrac.domain.protocol.JsonRpcResponse

interface JsonRpcClient {
    fun call(jsonRpcRequest: JsonRpcRequest): JsonRpcResponse
}