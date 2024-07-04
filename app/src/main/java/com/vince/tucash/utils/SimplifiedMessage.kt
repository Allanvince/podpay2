package com.vince.tucash.utils

import org.json.JSONException
import org.json.JSONObject

object SimplifiedMessage {
    fun get(stringMessage: String): HashMap<String, String> {
        val message = HashMap<String, String>()

        try {
            val jsonObject = JSONObject(stringMessage)

            // Check if "message" key exists in the JSON object
            if (jsonObject.has("message")) {
                val jsonMessages = jsonObject.getJSONObject("message")
                jsonMessages.keys().forEach { message[it] = jsonMessages.getString(it) }
            } else {
                // Handle the case where "message" key is missing
                message["error"] = "No 'message' key found in the JSON object"
            }
        } catch (e: JSONException) {
            // Handle JSON parsing error
            message["error"] = "Error parsing JSON: ${e.message}"
        }

        return message
    }
}
