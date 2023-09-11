package ru.magomedcoder.jsonrpc

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ru.magomedcoder.jsonrpc.domain.parser.ResultParser
import java.lang.reflect.Type

class MoshiResultParser(
    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
) : ResultParser {

    override fun <T> parse(type: Type, data: Any): T {
        return moshi.adapter<T>(type).fromJsonValue(data)
            ?: throw IllegalStateException("Неожиданно получено пустое значение при разборе JSON: $data!")
    }
}