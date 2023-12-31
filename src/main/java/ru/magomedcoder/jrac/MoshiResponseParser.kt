package ru.magomedcoder.jrac

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ru.magomedcoder.jrac.domain.parser.ResponseParser
import ru.magomedcoder.jrac.domain.protocol.JsonRpcResponse

class MoshiResponseParser(
    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
) : ResponseParser {

    override fun parse(data: ByteArray): JsonRpcResponse {
        val responseType = JsonRpcResponse::class.java
        val adapter = moshi.adapter(responseType)
        return adapter.fromJson(data.decodeToString())
            ?: throw IllegalStateException("Неожиданно получено пустое значение при разборе JSON: $data!")
    }
}