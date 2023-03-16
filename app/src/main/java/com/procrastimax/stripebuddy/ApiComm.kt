package com.procrastimax.stripebuddy

import android.widget.Toast
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import kotlin.coroutines.coroutineContext

const val APICommTag = "ApiComm"

/**
 * APIComm - a class to establish
 */
class APIComm {
    private val healthURL: String = "health"
    private val setValuesURL: String = "setRGBA"
    private val getValueURL: String = "setRGBA"


    private val client = OkHttpClient()

    data class APIResponse(val responseCode: Int, val responseBody: String)

    /**
     * A helper function to check the response of a HTTP request and log some useful information.
     * Handles IOException of the HTTP execute function.
     * All valid requests are returned, even ones with code 400 etc.
     *
     * @param request : Request - the already built OKHTTP request
     * @return @APIResponse - returns the HTTP code and the response body string, if there was one
     */
    private fun execRequest(request: Request): Result<APIResponse> {
        val response: Response = try {
            client.newCall(request).execute()
        } catch (e: Exception) {
            return Result.failure(Exception("Exception when executing GET request $e"))
        }

        return response.body?.run {
            Result.success(APIResponse(response.code, this.string()))
        } ?: Result.success(APIResponse(response.code, ""))
    }

    /**
     * Creates and executes the API call to change the brightness, which affects all other channels.
     * @param value : Int (0-100)
     */
    fun setAlphaValue(endpoint: String, port: Int, alpha: Int): Result<APIResponse> {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(endpoint).port(port)
                .addPathSegment(setValuesURL)
                .addQueryParameter("a", alpha.toString())
                .build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }

    /**
     * Creates and executes the API call to change the red channel.
     * @param value : Int (0-255)
     */
    fun setRedValue(endpoint: String, port: Int, red: Int): Result<APIResponse> {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(endpoint).port(port)
                .addPathSegment(setValuesURL)
                .addQueryParameter("r", red.toString())
                .build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }

    /**
     * Creates and executes the API call to change the green channel.
     * @param value : Int (0-255)
     */
    fun setGreenValue(endpoint: String, port: Int, green: Int): Result<APIResponse> {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(endpoint).port(port)
                .addPathSegment(setValuesURL)
                .addQueryParameter("g", green.toString())
                .build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }

    /**
     * Creates and executes the API call to change the blue channel.
     * @param value : Int (0-255)
     */
    fun setBlueValue(endpoint: String, port: Int, blue: Int): Result<APIResponse> {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(endpoint).port(port)
                .addPathSegment(setValuesURL)
                .addQueryParameter("b", blue.toString())
                .build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }

    /**
     * Creates and executes the API call to change all channels (RGBA).
     * @param r,g,b,a : Int (0-255)
     */
    fun setValues(
        endpoint: String,
        port: Int,
        red: Int,
        green: Int,
        blue: Int,
        alpha: Int
    ): Result<APIResponse> {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(endpoint).port(port)
                .addPathSegment(setValuesURL)
                .addQueryParameter("r", red.toString())
                .addQueryParameter("g", green.toString())
                .addQueryParameter("b", blue.toString())
                .addQueryParameter("a", alpha.toString())
                .build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }

    /**
     * Creates and executes the API call to get all channel values.
     */
    fun getValues(endpoint: String, port: Int): Result<APIResponse> {
        return try {
            val url: HttpUrl =
                HttpUrl.Builder().scheme("http").host(endpoint).port(port)
                    .addPathSegment(getValueURL)
                    .build()
            val request: Request = Request.Builder().url(url).build()
            execRequest(request)
        } catch (e : java.lang.IllegalArgumentException) {
            Result.failure(e)
        }
    }

    /**
     * A check to the /health endpoint to check availability of the backend.
     */
    fun checkHealth(endpoint: String, port: Int): Result<APIResponse> {
        val url: HttpUrl =
            HttpUrl.Builder().scheme("http").host(endpoint).port(port)
                .addPathSegment(healthURL).build()
        val request: Request = Request.Builder().url(url).build()
        return execRequest(request)
    }
}