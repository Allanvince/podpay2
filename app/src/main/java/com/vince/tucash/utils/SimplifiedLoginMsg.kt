package com.vince.tucash.utils

import org.json.JSONException
import org.json.JSONObject

object SimplifiedLoginMsg {
    fun get(stringMessage: String): HashMap<String, String> {
        val error = HashMap<String, String>()

        try {
            val jsonObject = JSONObject(stringMessage)

            // Check if "message" key exists in the JSON object
            if (jsonObject.has("error")) {
                val jsonMessages = jsonObject.getJSONObject("message")
                jsonMessages.keys().forEach { error[it] = jsonMessages.getString(it) }
            } else {
                // Handle the case where "message" key is missing
                error["error"] = "No 'message' key found in the JSON object"
            }
        } catch (e: JSONException) {
            // Handle JSON parsing error
            error["error"] = "Error parsing JSON: ${e.message}"
        }

        return error
    }
}
