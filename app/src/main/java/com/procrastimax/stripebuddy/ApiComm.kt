package com.procrastimax.stripebuddy

import android.util.Log
import okhttp3.*
import java.io.IOException

public const val TAG: String = "ApiComm"

/**
 * ApiComm - a class to establish
 */
class ApiComm {
    private val BASE_URL: String = BuildConfig.API_URL
    private val OFF_URL: String = "off"
    private val HEALTH_URL: String = "health"
    private val RED_VALUE_URL: String = "r/<VALUE>"
    private val GREEN_VALUE_URL: String = "g/<VALUE>"
    private val BLUE_VALUE_URL: String = "n/<VALUE>"
    private val ALL_VALUE_URL: String = "all/<VALUE1>,<VALUE2>,<VALUE3>"

    private val model = RGBViewModel()
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
        if (model.setRedValue(value)) {
            val url =
                BASE_URL + RED_VALUE_URL.replace("<VALUE>", model.getRedValue().toString())
            val request: Request = Request.Builder().url(url).build()
            execRequest(request);
            return true
        }
        return false;
    }

    /**
     * Creates and executes the API call to change the green channel.
     * @param value : Int (0-255)
     * @return Boolean - returns success indication by checking HTTP status
     */
    fun setGreenValue(value: Int): Boolean {
        if (model.setGreenValue(value)) {
            val url =
                BASE_URL + GREEN_VALUE_URL.replace("<VALUE>", model.getGreenValue().toString())
            val request: Request = Request.Builder().url(url).build()
            execRequest(request);
            return true
        }
        return false;
    }

    /**
     * Creates and executes the API call to change the blue channel.
     * @param value : Int (0-255)
     * @return Boolean - returns success indication by checking HTTP status
     */
    fun setBlueValue(value: Int): Boolean {
        if (model.setBlueValue(value)) {
            val url =
                BASE_URL + BLUE_VALUE_URL.replace("<VALUE>", model.getBlueValue().toString())
            val request: Request = Request.Builder().url(url).build()
            execRequest(request);
            return true
        }
        return false;
    }

    /**
     * Creates and executes the API call to change all channels to the specified value.
     * @param value : Int (0-255)
     * @return Boolean - returns success indication by checking HTTP status
     */
    fun setAlLValues(value: Int): Boolean {
        if (model.setAllValues(value)) {
            val url =
                BASE_URL + ALL_VALUE_URL.replace("<VALUE1>", model.getRedValue().toString())
                    .replace("<VALUE2>", model.getGreenValue().toString())
                    .replace("<VALUE3>", model.getBlueValue().toString())
            val request: Request = Request.Builder().url(url).build()
            execRequest(request);
            return true
        }
        return false;
    }

    /**
     * Creates and executes the API call to change all channels to the 3 given values.
     * @param r,g,b : Int (0-255)
     * @return Boolean - returns success indication by checking HTTP status
     */
    fun setValues(r: Int, g: Int, b: Int): Boolean {
        if (model.setValues(r, g, b)) {
            val url =
                BASE_URL + ALL_VALUE_URL.replace("<VALUE1>", model.getRedValue().toString())
                    .replace("<VALUE2>", model.getGreenValue().toString())
                    .replace("<VALUE3>", model.getBlueValue().toString())
            val request: Request = Request.Builder().url(url).build()
            execRequest(request);
            return true
        }
        return false;
    }

    /**
     * Creates and executes the API call to change all channels to the value 0 (aka off state).
     * @return Boolean - returns success indication by checking HTTP status
     */
    fun setOff(): Boolean {
        if (model.setAllValues(0)) {
            val url = BASE_URL + OFF_URL
            val request: Request = Request.Builder().url(url).build()
            execRequest(request)
            return true
        }
        return false
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