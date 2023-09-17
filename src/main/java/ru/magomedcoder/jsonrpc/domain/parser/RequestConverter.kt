package ru.magomedcoder.jsonrpc.domain.parser

import ru.magomedcoder.jsonrpc.domain.protocol.JsonRpcRequest

interface RequestConverter {
    fun convert(request: JsonRpcRequest): String
}