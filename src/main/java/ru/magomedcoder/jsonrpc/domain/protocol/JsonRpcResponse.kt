package ru.magomedcoder.jsonrpc.domain.protocol

data class JsonRpcResponse(
    val id: Long,
    val result: Any?
)