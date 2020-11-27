package io.davidosemwota.core.network.responses.symbols

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import java.lang.reflect.Type
import timber.log.Timber

/**
 * Deserializer class for Fixer IO API response
 */
class SymbolsResponseParser : JsonDeserializer<BaseResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BaseResponse? {
        val result = BaseResponse()

        try {
            json?.let {
                result.data = readSymbolResponseJson(it.asJsonObject)
            }
        } catch (e: JsonSyntaxException) {
            Timber.d("Error Parsing response")
            Timber.d(e.printStackTrace().toString())
            return null
        }

        return result
    }

    /**
     * Converts all currency symbols from raw JSON from API to HashMap.
     */
    private fun readSymbolResponseJson(jsonObject: JsonObject): HashMap<String, String> {

        val gson = Gson()
        val allSymbols = HashMap<String, String>()

        for (entry: Map.Entry<String, JsonElement> in jsonObject.entrySet()) {
            allSymbols[entry.key] = gson.fromJson(entry.value, String::class.java)
        }

        return allSymbols
    }
}
