package com.example.azorshareapp.services.network

import android.os.AsyncTask
import com.example.azorshareapp.services.network.NetworkTaskCallback
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class NetworkTask(
    private val callback: NetworkTaskCallback,
    private val json: String,
    private val path: String
) :
    AsyncTask<Void, Void, Boolean>() {

    private var jsonResponse: String? = null

    companion object {
        private val client = OkHttpClient.Builder()
            .cookieJar(object : CookieJar {
                private val cookieStore = HashMap<String, List<Cookie>>()

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    cookieStore[url.host] = cookies
                }

                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    return cookieStore[url.host] ?: ArrayList()
                }
            })
            .build()
    }

    override fun doInBackground(vararg params: Void): Boolean {
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()

        val body = RequestBody.create(JSON, json)
        val request = Request.Builder()
            .url("https://azorshare-gateway.herokuapp.com/$path")
            .post(body)
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                jsonResponse = response.body?.string()
                println(response)
                true
            } else {
                // The API returned an error response
                false
            }
        } catch (e: IOException) {
            // There was an error connecting to the API
            e.printStackTrace()
            false
        }
    }

    override fun onPostExecute(result: Boolean) {
        jsonResponse?.let { callback.onNetworkTaskComplete(result, it) }
    }
}
