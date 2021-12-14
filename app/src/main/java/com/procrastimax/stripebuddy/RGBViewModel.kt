package com.procrastimax.stripebuddy

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val ViewModelTAG = "ViewModelTAG"

class RGBViewModel : ViewModel() {

    private val api = APIComm()

    // a variable which stores if the API is reachable and therefore the values from the model can be used in this view
    var isReachable: Boolean = false

    private val rgbModel: MutableLiveData<RGBModel> by lazy {
        MutableLiveData<RGBModel>().also {
            fetchColors()
        }
    }

    /**
     * Retrieves current color values from the API (r,g,b,brightness) and updates the model accordingly
     * If the API is not reachable, a specific flag is set.
     */
    fun fetchColors() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                RGBModel().apply {
                    // check APIs health status
                    api.checkHealth().onSuccess { res ->
                        // check status code
                        if (res.responseCode == 200) {
                            isReachable = true

                            // make getValues call, check response for formatting and reachability
                            api.getValues().onSuccess { valueRes ->
                                if (valueRes.responseCode == 200) {
                                    valueRes.responseBody?.let {
                                        RGBModel.parseStringToModel(it).onSuccess { rgbbr ->
                                            setAbsoluteRedValue(rgbbr.r)
                                            setAbsoluteGreenValue(rgbbr.g)
                                            setAbsoluteBlueValue(rgbbr.b)
                                            setBrightnessValue(rgbbr.brightness)
                                        }.onFailure { err ->
                                            Log.e(ViewModelTAG, "On parsing getValues result: $err")
                                        }
                                    } ?: run {
                                        Log.w(
                                            ViewModelTAG,
                                            "Received HTTP response with Code: ${valueRes.responseCode}, but not response body!"
                                        )
                                    }
                                } else {
                                    Log.w(
                                        ViewModelTAG,
                                        "Received HTTP response with Code: ${valueRes.responseCode}, Msg: ${valueRes.responseBody}"
                                    )
                                }
                            }.onFailure { valueErr ->
                                isReachable = false
                                Log.e(ViewModelTAG, "API is not reachable!")
                                valueErr.localizedMessage?.let {
                                    Log.e(ViewModelTAG, it)
                                }
                            }
                        } else {
                            isReachable = true
                            Log.w(
                                ViewModelTAG,
                                "Received HTTP response with Code: ${res.responseCode}, Msg: ${res.responseBody}"
                            )
                        }
                    }.onFailure { err ->
                        isReachable = false
                        Log.e(ViewModelTAG, "API is not reachable!")
                        err.localizedMessage?.let {
                            Log.e(ViewModelTAG, it)
                        }
                    }
                }
            }
            rgbModel.value = result
        }
    }

    fun getRGBModel(): LiveData<RGBModel> {
        return rgbModel
    }

    fun changeRedChannel(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            rgbModel.value?.let { model ->
                // only change red value if the requested value is ok
                if (model.setAbsoluteRedValue(value)) {
                    api.setRedValue(model.redValue).onSuccess {
                        if (it.responseCode != 200) {
                            Log.w(
                                ViewModelTAG,
                                "Something went wrong when trying to change red channel! HTTP Code: ${it.responseCode} ${it.responseBody}"
                            )
                        }
                    }.onFailure { err ->
                        Log.e(ViewModelTAG, "Could not change red channel! Error: $err")
                    }
                }
            }
        }
    }

    fun changeGreenChannel(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            rgbModel.value?.let { model ->
                if (model.setAbsoluteGreenValue(value)) {
                    api.setGreenValue(model.greenValue).onSuccess {
                        if (it.responseCode != 200) {
                            Log.w(
                                ViewModelTAG,
                                "Something went wrong when trying to change green channel! HTTP Code: ${it.responseCode} ${it.responseBody}"
                            )
                        }
                    }.onFailure { err ->
                        Log.e(ViewModelTAG, "Could not change green channel! Error: $err")
                    }
                }
            }
        }
    }

    fun changeBlueChannel(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            rgbModel.value?.let { model ->
                if (model.setAbsoluteBlueValue(value)) {
                    api.setBlueValue(model.blueValue).onSuccess {
                        if (it.responseCode != 200) {
                            Log.w(
                                ViewModelTAG,
                                "Something went wrong when trying to change blue channel! HTTP Code: ${it.responseCode}  ${it.responseBody}"
                            )
                        }
                    }.onFailure { err ->
                        Log.e(ViewModelTAG, "Could not change blue channel! Error: $err")
                    }
                }
            }
        }
    }

    fun changeBrightness(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            rgbModel.value?.let { model ->
                if (model.setBrightnessValue(value)) {
                    api.setBrightnessValue(model.brightness).onSuccess {
                        if (it.responseCode != 200) {
                            Log.w(
                                ViewModelTAG,
                                "Something went wrong when trying to change brightness! HTTP Code: ${it.responseCode}  ${it.responseBody}"
                            )
                        }
                    }.onFailure { err ->
                        Log.e(ViewModelTAG, "Could not change brightness! Error: $err")
                    }
                }
            }
        }
    }
}