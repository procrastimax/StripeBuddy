package com.procrastimax.stripebuddy

import android.util.Log
import okhttp3.*
import java.io.IOException

const val APITAG: String = "API"

// TODO: wait for result of execRequest with Futures?

/**
 * ApiComm - a class to establish
 */
class ApiComm {
    private val BASE_URL: String = "stripe.fritz.box"
    private val API_PORT : Int = 8000
    private val OFF_URL: String = "off"
    private val HEALTH_URL: String = "health"
    private val RED_VALUE_URL: String = "r"
    private val GREEN_VALUE_URL: String = "g"
    private val BLUE_VALUE_URL: String = "b"
    private val SETVALUES_URL: String = "setValues"
    private val GETVALUES_URL: String = "getValues"

    private val client = OkHttpClient()

    /**
     * A helper function to check the response of a HTTP request and log some useful information.
     * Handles IOException of the HTTP execute function.
     * @param request : Request - the already built OKHTTP request
     * @return Boolean - returns a boolean to indicate success of the response
     */
    private fun execRequest(request: Request){
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(APITAG, "URL: ${call.request().url} - Exception: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d(APITAG, "URL: ${call.request().url} - [${response.code}]")
                } else {
                    Log.w(
                        APITAG,
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
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT).addPathSegment(RED_VALUE_URL)
                .addPathSegment(value.toString())
                .build()
        val request: Request = Request.Builder().url(url).build()
        execRequest(request)
        return true
    }

    fun getRedValue(): Int {
        val url = "$BASE_URL$RED_VALUE_URL/{value.toString()}"
        val request: Request = Request.Builder().url(url).build()
        execRequest(request);
        return 0
    }

    /**
     * Creates and executes the API call to change the green channel.
     * @param value : Int (0-255)
     * @return Boolean - returns success indication by checking HTTP status
     */
    fun setGreenValue(value: Int): Boolean {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT).addPathSegment(GREEN_VALUE_URL)
                .addPathSegment(value.toString())
                .build()
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
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT).addPathSegment(BLUE_VALUE_URL)
                .addPathSegment(value.toString())
                .build()
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
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT).addPathSegment(SETVALUES_URL)
                .addQueryParameter("r", r.toString()).addQueryParameter("g", g.toString())
                .addQueryParameter("b", b.toString())
                .build()
        val request: Request = Request.Builder().url(url).build()
        execRequest(request)
        return true
    }

    /**
     * Creates and executes the API call to change all channels to the value 0 (aka off state).
     * @return Boolean - returns success indication by checking HTTP status
     */
    fun setOff(): Boolean {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT).addPathSegment(OFF_URL).build()
        val request: Request = Request.Builder().url(url).build()
        execRequest(request)
        return true
    }

    /**
     * A check to the /health endpoint to check availability of the backend.
     * @return Boolean - returns true if the backend properly response with HTTP 200 code, else returns false
     */
    fun checkHealth(): Boolean {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT).addPathSegment(HEALTH_URL).build()
        val request: Request = Request.Builder().url(url).build()
        execRequest(request)
        return true
    }
}