package ru.magomedcoder.jsonrpc.domain

import ru.magomedcoder.jsonrpc.domain.protocol.JsonRpcRequest
import ru.magomedcoder.jsonrpc.domain.protocol.JsonRpcResponse

interface JsonRpcClient {
    fun call(jsonRpcRequest: JsonRpcRequest): JsonRpcResponse
}