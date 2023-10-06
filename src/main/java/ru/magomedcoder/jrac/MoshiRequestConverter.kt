package ru.magomedcoder.jrac

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ru.magomedcoder.jrac.domain.parser.RequestConverter
import ru.magomedcoder.jrac.domain.protocol.JsonRpcRequest

class MoshiRequestConverter(
    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
) : RequestConverter {

    override fun convert(request: JsonRpcRequest): String {
        return moshi.adapter(JsonRpcRequest::class.java).toJson(request)
    }
}