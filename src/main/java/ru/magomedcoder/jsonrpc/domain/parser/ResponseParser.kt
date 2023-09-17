package ru.magomedcoder.jsonrpc.domain.parser

import ru.magomedcoder.jsonrpc.domain.protocol.JsonRpcResponse

interface ResponseParser {
    fun parse(data: ByteArray): JsonRpcResponse
}