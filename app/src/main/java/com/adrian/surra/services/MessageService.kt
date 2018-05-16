package com.adrian.surra.services

import android.util.Log
import com.adrian.surra.controller.App
import com.adrian.surra.model.Channel
import com.adrian.surra.model.Message
import com.adrian.surra.utilities.GET_CHANNELS_URL
import com.adrian.surra.utilities.GET_MESSAGES_URL
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONException

object MessageService {

    var channels = ArrayList<Channel>()
    var messages = ArrayList<Message>()

    // Add jsonArray request to queue, set content-type and auth header, expect jsonArray
    // loop over array, extract each object and it's fields, store each field into a model object
    // and add it into channels array list
    fun getChannels(complete: (Boolean) -> Unit) {

        val channelsRequest = object : JsonArrayRequest(Method.GET, GET_CHANNELS_URL, null, Response.Listener { response ->

            try {

                for (x in 0 until response.length()) {
                    val channel = response.getJSONObject(x)

                    val name = channel.getString("name")
                    val description = channel.getString("description")
                    val id = channel.getString("_id")

                    val newChannel = Channel(name, description, id)
                    this.channels.add(newChannel)
                }
                complete(true)

            } catch (e: JSONException) {
                Log.d("JSON", "EXC:" + e.localizedMessage)
                complete(false)
            }

        }, Response.ErrorListener { error ->  
            Log.d("ERROR", "Could not retrieve channels: $error")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("x-auth", App.sharedPreferences.authToken)

                return headers
            }
        }

        App.sharedPreferences.requestQueue.add(channelsRequest)
    }

    // Pass channel id to jsonArray request, set content-type, auth header and expect jsonArray
    // with messages by channel id, then clear old messages array before add new values.
    // Loop over array, extract each object and it's fields, store each field into a model object
    // and add it into messages array list
    fun getMessages(channelId: String, complete: (Boolean) -> Unit) {

        val url = "$GET_MESSAGES_URL$channelId"
        
        val messagesRequest = object: JsonArrayRequest(Method.GET, url, null, Response.Listener { response ->

            clearMessages()
            try {

                for (x in 0 until response.length()) {
                    val message = response.getJSONObject(x)
                    val messageBody = message.getString("messageBody")
                    val channelId = message.getString("channelId")
                    val id = message.getString("_id")
                    val userName = message.getString("userName")
                    val userAvatar = message.getString("userAvatar")
                    val userAvatarColor = message.getString("userAvatarColor")
                    val timeStamp = message.getString("timeStamp")

                    val newMessage = Message(messageBody, userName,channelId, userAvatar, userAvatarColor, id, timeStamp)
                    this.messages.add(newMessage)
                }
                complete(true)

            } catch (e: JSONException) {
                Log.d("JSON", "EXC:" + e.localizedMessage)
                complete(false)
            }

        }, Response.ErrorListener { error ->  
            Log.d("ERROR", "Could not retrieve messages: $error")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("x-auth", App.sharedPreferences.authToken)

                return headers
            }
        }
        App.sharedPreferences.requestQueue.add(messagesRequest)
    }

    fun clearChannels() {
        channels.clear()
    }

    fun clearMessages() {
        messages.clear()
    }
}