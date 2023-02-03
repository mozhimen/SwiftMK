package com.mozhimen.basick.utilk.json

import android.text.TextUtils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * @ClassName UtilKJson
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/3 17:30
 * @Version 1.0
 */
object UtilKJson {

    @JvmStatic
    fun wrapJson(jsonStr: String): String {
        var message: String
        if (TextUtils.isEmpty(jsonStr)) return ""
        try {
            if (jsonStr.startsWith("{")) {
                val jsonObject = JSONObject(jsonStr)
                message = jsonObject.toString(2)
                message = """
                
                <<<<<=====JSONObject=====>>>>>
                $message
                <<<<<=====JSONObject=====>>>>>
                
                """.trimIndent()
            } else if (jsonStr.startsWith("[")) {
                val jsonArray = JSONArray(jsonStr)
                message = jsonArray.toString(4)
                message = """
                
                <<<<<=====JSONArray=====>>>>>
                $message
                <<<<<=====JSONArray=====>>>>>
                
                """.trimIndent()
            } else {
                message = jsonStr
            }
        } catch (e: JSONException) {
            message = jsonStr
        }
        return message
    }

    /**
     * 分割json
     * @param json String
     * @return Array<String?>?
     */
    @JvmStatic
    fun splitJson(json: String): Array<String?>? {
        val splitArray: Array<String?> = json.split("\t").toTypedArray()
        return if (splitArray.size != 2) null else splitArray
    }

    /**
     * Any转JsonObj
     * @param any Any
     * @return JSONObject?
     */
    @JvmStatic
    fun any2JsonObj(any: Any): JSONObject =
        if (any is String) {
            JSONObject(any)
        } else {
            JSONObject(UtilKJsonGson.any2Json(any))
        }

    /**
     * 从JsonString中摘取string
     * @param json String
     * @param name String
     * @return String?
     */
    @JvmStatic
    fun getStrFromJson(json: String, name: String): String =
        JSONObject(json.trim { it <= ' ' })[name].toString()

    /**
     * json转Array
     * @param json String
     * @return Array<String>?
     */
    @JvmStatic
    fun json2StrArray(json: String): Array<String?> {
        val jsonArray = JSONArray(json.trim { it <= ' ' })
        val length = jsonArray.length()
        val strArr = Array<String?>(length) { "" }
        for (i in 0 until length) {
            strArr[i] = jsonArray[i] as? String?
        }
        return strArr
    }

    /**
     * 从JsonObj找到JsonArray
     * @param jsonObj JSONObject
     * @param name String
     * @return JSONArray?
     */
    @JvmStatic
    fun getJsonArrayFromJsonObj(jsonObj: JSONObject, name: String): JSONArray? {
        return try {
            jsonObj.getJSONArray(name)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 从JsonObj找到JsonObj
     * @param jsonObj JSONObject
     * @param name String
     * @return JSONObject?
     */
    @JvmStatic
    fun getJsonObjFromJsonObj(jsonObj: JSONObject, name: String): JSONObject? {
        return try {
            jsonObj.getJSONObject(name)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * jsonArray转StrList
     * @param jsonArray JSONArray
     * @return ArrayList<String?>?
     */
    @JvmStatic
    fun jsonArray2StrList(jsonArray: JSONArray): ArrayList<String?>? {
        val arrayList = ArrayList<String?>()
        for (i in 0 until jsonArray.length()) {
            try {
                val obj = jsonArray[i] as? String?
                arrayList.add(obj)
            } catch (e: JSONException) {
                e.printStackTrace()
                return null
            }
        }
        return arrayList
    }

    /**
     * jsonArray转TList
     * @param jsonArray JSONArray
     * @param cls Class<T>
     * @return ArrayList<T?>?
     */
    @JvmStatic
    fun <T> jsonArray2TList(jsonArray: JSONArray, cls: Class<T>): ArrayList<T?>? {
        val arrayList = ArrayList<T?>()
        try {
            val length = jsonArray.length()
            for (i in 0 until length) {
                val jsonObj = jsonArray[i] as? JSONObject?
                if (jsonObj != null) {
                    arrayList.add(UtilKJsonGson.json2T(jsonObj.toString(), cls))
                } else {
                    arrayList.add(null)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return arrayList
    }

    /**
     * json转TList
     * @param json String
     * @param cls Class<T>
     * @return ArrayList<T?>?
     */
    @JvmStatic
    fun <T> json2TList(json: String, cls: Class<T>): ArrayList<T?>? {
        return jsonArray2TList(JSONArray(json.trim { json <= " " }), cls)
    }

    /**
     * 组合Json
     * @param jsonObj JSONObject
     * @param jsonObj2 JSONObject
     * @return String?
     */
    @JvmStatic
    fun combineJson(jsonObj: JSONObject, jsonObj2: JSONObject): String? {
        val jsonArray = JSONArray()
        jsonArray.put(jsonObj)
        val jsonArray2 = JSONArray()
        jsonArray2.put(jsonObj2)

        val stringBuffer = StringBuffer()
        val stringBuilder = StringBuilder()
        stringBuilder.append(jsonArray.toString())
        stringBuilder.append("\t")
        stringBuilder.append(jsonArray2.toString())
        stringBuffer.append(stringBuilder.toString())
        return stringBuffer.toString()
    }

    /**
     * json转JsonObj
     * @param json String
     * @return JSONObject
     */
    @JvmStatic
    fun json2JsonObj(json: String): JSONObject =
        JSONObject(json)

    /**
     * json转JsonArray
     * @param json String
     * @return JSONArray
     */
    @JvmStatic
    fun json2JsonArray(json: String): JSONArray =
        JSONArray(json)

    /**
     * 将obj填入
     * @param jsonObj JSONObject
     * @param any Any
     * @param strObj Array<out String>
     */
    @JvmStatic
    fun putAny2JsonObj(jsonObj: JSONObject, any: Any, vararg strObj: String) {
        var name = strObj[0]
        if (strObj.size > 1) {
            name = strObj[1]
        }
        try {
            jsonObj.put(name, any)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 从jsonObj获取T
     * @param jsonObj JSONObject
     * @param name String
     * @return T?
     */
    @JvmStatic
    fun <T> getTFromJsonObj(jsonObj: JSONObject, name: String): T? =
        try {
            jsonObj[name] as? T?
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }

    /**
     * 从JsonObj获取JsonArray
     * @param jsonArray JSONArray
     * @param i Int
     * @return JSONObject?
     */
    @JvmStatic
    fun getJsonObjFromJsonArray(jsonArray: JSONArray, i: Int): JSONObject? =
        try {
            jsonArray.getJSONObject(i)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
}