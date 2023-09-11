package ru.magomedcoder.jsonrpc

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ru.magomedcoder.jsonrpc.domain.parser.RequestConverter
import ru.magomedcoder.jsonrpc.domain.protocol.JsonRpcRequest

class MoshiRequestConverter(
    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
) : RequestConverter {

    override fun convert(request: JsonRpcRequest): String {
        return moshi.adapter(JsonRpcRequest::class.java).toJson(request)
    }
}