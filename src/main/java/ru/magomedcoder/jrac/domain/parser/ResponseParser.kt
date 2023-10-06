package ru.magomedcoder.jrac.domain.parser

import ru.magomedcoder.jrac.domain.protocol.JsonRpcResponse

interface ResponseParser {
    fun parse(data: ByteArray): JsonRpcResponse
}