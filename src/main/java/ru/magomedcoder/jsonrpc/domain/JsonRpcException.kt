package ru.magomedcoder.jsonrpc.domain

data class JsonRpcException(
    override val message: String,
    val code: Int,
    val data: Any?
) : RuntimeException(message)