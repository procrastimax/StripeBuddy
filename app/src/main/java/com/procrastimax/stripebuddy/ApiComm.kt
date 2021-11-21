package com.procrastimax.stripebuddy

import android.util.Log
import okhttp3.*
import java.io.IOException

const val TAG: String = "API"

// TODO: wait for result of execRequest with Futures?

/**
 * ApiComm - a class to establish
 */
class ApiComm {
    private val BASE_URL: String = BuildConfig.API_URL
    private val OFF_URL: String = "off"
    private val HEALTH_URL: String = "health"
    private val RED_VALUE_URL: String = "r/<VALUE>"
    private val GREEN_VALUE_URL: String = "g/<VALUE>"
    private val BLUE_VALUE_URL: String = "b/<VALUE>"
    private val ALL_VALUE_URL: String = "all/<VALUE1>,<VALUE2>,<VALUE3>"

    private val client = OkHttpClient()

    /**
     * A helper function to check the response of a HTTP request and log some useful information.
     * Handles IOException of the HTTP execute function.
     * @param request : Request - the already built OKHTTP request
     * @return Boolean - returns a boolean to indicate success of the response
     */
    private fun execRequest(request: Request) {
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "URL: ${call.request().url} - Exception: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d(TAG, "URL: ${call.request().url} - [${response.code}]")
                } else {
                    Log.w(
                        TAG,
                        "URL: ${call.request().url} - [${response.code}] Response: ${response.body}"
                    )
                }
                response.body?.close()
            }
        })
    }

    /**
     * Creates and executes the API call to change the red channel.
     * @param value : Int (0-255)
     * @return Boolean - returns success indication by checking HTTP status
     */
    fun setRedValue(value: Int): Boolean {
        val url = BASE_URL + RED_VALUE_URL.replace("<VALUE>", value.toString())
        val request: Request = Request.Builder().url(url).build()
        execRequest(request);
        return true
    }

    /**
     * Creates and executes the API call to change the green channel.
     * @param value : Int (0-255)
     * @return Boolean - returns success indication by checking HTTP status
     */
    fun setGreenValue(value: Int): Boolean {
        val url =
            BASE_URL + GREEN_VALUE_URL.replace("<VALUE>", value.toString())
        val request: Request = Request.Builder().url(url).build()
        execRequest(request);
        return true
    }

    /**
     * Creates and executes the API call to change the blue channel.
     * @param value : Int (0-255)
     * @return Boolean - returns success indication by checking HTTP status
     */
    fun setBlueValue(value: Int): Boolean {
        val url = BASE_URL + BLUE_VALUE_URL.replace("<VALUE>", value.toString())
        val request: Request = Request.Builder().url(url).build()
        execRequest(request);
        return true
    }

    /**
     * Creates and executes the API call to change all channels to the specified value.
     * @param value : Int (0-255)
     * @return Boolean - returns success indication by checking HTTP status
     */
    fun setAlLValues(value: Int): Boolean {
        val url = BASE_URL + ALL_VALUE_URL.replace("<VALUE1>", value.toString())
            .replace("<VALUE2>", value.toString())
            .replace("<VALUE3>", value.toString())
        val request: Request = Request.Builder().url(url).build()
        execRequest(request);
        return true
    }

    /**
     * Creates and executes the API call to change all channels to the 3 given values.
     * @param r,g,b : Int (0-255)
     * @return Boolean - returns success indication by checking HTTP status
     */
    fun setValues(r: Int, g: Int, b: Int): Boolean {
        val url = BASE_URL + ALL_VALUE_URL.replace("<VALUE1>", r.toString())
            .replace("<VALUE2>", g.toString())
            .replace("<VALUE3>", b.toString())
        val request: Request = Request.Builder().url(url).build()
        execRequest(request);
        return true
    }

    /**
     * Creates and executes the API call to change all channels to the value 0 (aka off state).
     * @return Boolean - returns success indication by checking HTTP status
     */
    fun setOff(): Boolean {
        val url = BASE_URL + OFF_URL
        val request: Request = Request.Builder().url(url).build()
        execRequest(request)
        return true
    }

    /**
     * A check to the /health endpoint to check availability of the backend.
     * @return Boolean - returns true if the backend properly response with HTTP 200 code, else returns false
     */
    fun checkHealth(): Boolean {
        val url = BASE_URL + HEALTH_URL
        val request: Request = Request.Builder().url(url).build()
        execRequest(request)
        return true
    }
}