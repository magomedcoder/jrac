package ru.magomedcoder.jrac.domain.parser

import ru.magomedcoder.jrac.domain.protocol.JsonRpcRequest

interface RequestConverter {
    fun convert(request: JsonRpcRequest): String
}