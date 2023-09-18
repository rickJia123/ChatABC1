package river.chat.lib_core.utils.common

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object GsonKits {


    fun getStringValue(jObject: JsonObject, key: String?): String? {
        val element = jObject[key]
        return if (element != null && !element.isJsonNull) element.asString else null
    }

    fun getIntValue(jObject: JsonObject, key: String?): Int? {
        val element = jObject[key]
        return if (element != null && !element.isJsonNull) element.asInt else null
    }

    fun getDoubleValue(jObject: JsonObject, key: String?): Double? {
        val element = jObject[key]
        return if (element != null && !element.isJsonNull) element.asDouble else null
    }

    fun getBooleanValue(jObject: JsonObject, key: String?): Boolean? {
        val element = jObject[key]
        return if (element != null && !element.isJsonNull) element.asBoolean else null
    }

    fun getLongValue(jObject: JsonObject, key: String?): Long? {
        val element = jObject[key]
        return if (element != null && !element.isJsonNull) element.asLong else null
    }

    fun getJsonArrayValue(jObject: JsonObject, key: String?): JsonArray? {
        val element = jObject[key]
        return if (element != null && !element.isJsonNull) element.asJsonArray else null
    }

    /**
     * json解析成数组
     */
    fun <T> jsonToArrayList(json: String?, clazz: Class<T>?): ArrayList<T>? {
        val type = object : TypeToken<ArrayList<JsonObject?>?>() {}.type
        val jsonObjects: ArrayList<JsonObject>?
        jsonObjects = fromJson<ArrayList<JsonObject>>(json, type)
        if (jsonObjects == null) {
            return null
        }
        val arrayList = ArrayList<T>()
        for (jsonObject in jsonObjects) {
            val t = fromJson(jsonObject, clazz)
            if (t != null) {
                arrayList.add(t)
            }
        }
        return arrayList
    }

    fun <T> fromJson(jObject: JsonObject?, var1: Class<T>?): T? {
        return try {
            Gson().fromJson(jObject, var1)
        } catch (var3: Exception) {
            null
        }
    }

    fun <T> fromJson(var0: String?, var1: Class<T>?): T? {
        return try {
            Gson().fromJson(var0, var1)
        } catch (var3: Exception) {
            null
        }
    }

    fun <T> fromJson(var0: String?, var1: Type?): T? {
        return try {
            Gson().fromJson(var0, var1)
        } catch (var3: Exception) {
            null
        }
    }


    inline fun <reified T> fromJson(json: String?): T? {
        val nonNullJson = json ?: return null
        return  fromJson(nonNullJson, T::class.java)
    }

    fun toJson(var0: Any?): String? {
        return Gson().toJson(var0)
    }
}