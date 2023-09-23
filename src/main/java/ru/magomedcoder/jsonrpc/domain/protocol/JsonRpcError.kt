package ru.magomedcoder.jsonrpc.domain.protocol

data class JsonRpcError(
    val message: String,
    val code: Int,
    val data: Any?
)