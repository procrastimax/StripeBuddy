package com.procrastimax.stripebuddy

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request

const val APITAG: String = "API"

/**
 * APIComm - a class to establish
 */
class APIComm {
    private val BASE_URL: String = "stripe.fritz.box"
    private val API_PORT: Int = 8000
    private val OFF_URL: String = "off"
    private val HEALTH_URL: String = "health"
    private val BRIGHTNESS_VALUE_URL: String = "brightness"
    private val RED_VALUE_URL: String = "r"
    private val GREEN_VALUE_URL: String = "g"
    private val BLUE_VALUE_URL: String = "b"
    private val SETVALUES_URL: String = "setValues"
    private val GETVALUES_URL: String = "getValues"

    private val client = OkHttpClient()

    data class APIResponse(val responseCode: Int, val responseBody: String?)


    /**
     * A helper function to check the response of a HTTP request and log some useful information.
     * Handles IOException of the HTTP execute function.
     * All valid requests are returned, even ones with code 400 etc.
     *
     * @param request : Request - the already built OKHTTP request
     * @return @APIResponse - returns the HTTP code and the response body string, if there was one
     */
    private fun execRequest(request: Request): Result<APIResponse> {
        return try {
            client.newCall(request).execute().use { response ->
                Result.success(APIResponse(response.code, response.body?.string()))
            }
        } catch (e: Exception) {
            return Result.failure(Exception("Exception when executing GET request $e"))
        }
    }

    /**
     * Creates and executes the API call to change the brightness, which affects all other channels.
     * @param value : Int (0-100)
     */
    fun setBrightnessValue(value: Int): Result<APIResponse> {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT)
                .addPathSegment(BRIGHTNESS_VALUE_URL)
                .addPathSegment(value.toString())
                .build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }

    fun getBrightnessValue(): Result<APIResponse> {
        val url: HttpUrl = HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT)
            .addPathSegment(BRIGHTNESS_VALUE_URL)
            .build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }

    /**
     * Creates and executes the API call to change the red channel.
     * @param value : Int (0-255)
     */
    fun setRedValue(value: Int): Result<APIResponse> {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT)
                .addPathSegment(RED_VALUE_URL)
                .addPathSegment(value.toString())
                .build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }

    fun getRedValue(): Result<APIResponse> {
        val url: HttpUrl = HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT)
            .addPathSegment(RED_VALUE_URL)
            .build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }

    /**
     * Creates and executes the API call to change the green channel.
     * @param value : Int (0-255)
     */
    fun setGreenValue(value: Int): Result<APIResponse> {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT)
                .addPathSegment(GREEN_VALUE_URL)
                .addPathSegment(value.toString())
                .build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }

    fun getGreenValue(): Result<APIResponse> {
        val url: HttpUrl = HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT)
            .addPathSegment(GREEN_VALUE_URL)
            .build()
        val request: Request = Request.Builder().url(url).build()
        execRequest(request)
        return execRequest(request)
    }

    /**
     * Creates and executes the API call to change the blue channel.
     * @param value : Int (0-255)
     */
    fun setBlueValue(value: Int): Result<APIResponse> {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT)
                .addPathSegment(BLUE_VALUE_URL)
                .addPathSegment(value.toString())
                .build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }

    fun getBlueValue(): Result<APIResponse> {
        val url: HttpUrl = HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT)
            .addPathSegment(BLUE_VALUE_URL)
            .build()
        val request: Request = Request.Builder().url(url).build()
        execRequest(request)
        return execRequest(request)
    }

    /**
     * Creates and executes the API call to change all channels to the 3 given values.
     * @param r,g,b : Int (0-255)
     */
    fun setValues(r: Int, g: Int, b: Int): Result<APIResponse> {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT)
                .addPathSegment(SETVALUES_URL)
                .addQueryParameter("r", r.toString()).addQueryParameter("g", g.toString())
                .addQueryParameter("b", b.toString())
                .build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }

    /**
     * Creates and executes the API call to get all channel values.
     */
    fun getValues(): Result<APIResponse> {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT)
                .addPathSegment(GETVALUES_URL)
                .build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }

    /**
     * Creates and executes the API call to change all channels to the value 0 (aka off state).
     */
    fun setOff(): Result<APIResponse> {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT).addPathSegment(OFF_URL)
                .build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }

    /**
     * A check to the /health endpoint to check availability of the backend.
     */
    fun checkHealth(): Result<APIResponse> {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(BASE_URL).port(API_PORT)
                .addPathSegment(HEALTH_URL).build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }
}