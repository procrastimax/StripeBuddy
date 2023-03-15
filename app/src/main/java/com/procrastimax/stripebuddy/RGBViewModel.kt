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

    private val test_endpoint: String = "192.168.179.120"
    private val test_port: Int = 80

    private val rgbaModel: MutableLiveData<RGBAModel> by lazy {
        MutableLiveData<RGBAModel>().also {
            fetchColors()
        }
    }

    /**
     * Retrieves current color values from the API (r,g,b,brightness) and updates the model accordingly
     * If the API is not reachable, a specific flag is set.
     */
    private fun fetchColors() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                RGBAModel().apply {
                    api.getValues(test_endpoint, test_port).onSuccess { valueRes ->
                        if (valueRes.responseCode == 200) {
                            isReachable = true

                            if (valueRes.responseBody == "") {
                                Log.w(
                                    ViewModelTAG,
                                    "Received HTTP response with Code: ${valueRes.responseCode}, but empty response body!"
                                )
                            } else {
                                RGBAModel.parseStringToModel(valueRes.responseBody)
                                    .onSuccess { rgba ->
                                        setAllValuesAbsolute(rgba.r, rgba.g, rgba.b, rgba.a)
                                    }.onFailure {
                                        Log.e(
                                            ViewModelTAG,
                                            "On parsing getValues result: ${it.localizedMessage}"
                                        )
                                    }
                            }
                        } else {
                            Log.w(
                                ViewModelTAG,
                                "Received HTTP response with Error Code: ${valueRes.responseCode}, Msg: ${valueRes.responseBody}"
                            )
                        }
                    }.onFailure { valueErr ->
                        isReachable = false
                        Log.e(
                            ViewModelTAG,
                            "API is not reachable! Error: ${valueErr.localizedMessage}"
                        )
                    }
                }
            }
            rgbaModel.value = result
        }
    }


    fun getRGBModel(): LiveData<RGBAModel> {
        return rgbaModel
    }

    fun changeRedChannel(value: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                RGBAModel().apply {
                    rgbaModel.value?.let { model ->
                        if (model.setAbsoluteRedValue(value)) {
                            api.setRedValue(test_endpoint, test_port, model.redValue)
                                .onSuccess {
                                    if (it.responseCode != 200) {
                                        Log.w(
                                            ViewModelTAG,
                                            "Something went wrong when trying to change red channel! HTTP Code: ${it.responseCode} ${it.responseBody}"
                                        )
                                    } else {
                                        RGBAModel.parseStringToModel(it.responseBody)
                                            .onSuccess { rgba ->
                                                setAllValuesAbsolute(rgba.r, rgba.g, rgba.b, rgba.a)
                                            }.onFailure { err ->
                                                Log.e(
                                                    ViewModelTAG,
                                                    "Could not interpret red channel response value!! Error: ${err.localizedMessage}"
                                                )
                                            }

                                    }
                                }.onFailure { err ->
                                    Log.e(
                                        ViewModelTAG,
                                        "Could not change red channel! Error: $err"
                                    )
                                }
                        }
                    }
                }
            }
            rgbaModel.value = result
        }
    }

    fun changeGreenChannel(value: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                RGBAModel().apply {
                    rgbaModel.value?.let { model ->
                        if (model.setAbsoluteGreenValue(value)) {
                            api.setGreenValue(test_endpoint, test_port, model.greenValue)
                                .onSuccess {
                                    if (it.responseCode != 200) {
                                        Log.w(
                                            ViewModelTAG,
                                            "Something went wrong when trying to change green channel! HTTP Code: ${it.responseCode} ${it.responseBody}"
                                        )
                                    } else {
                                        RGBAModel.parseStringToModel(it.responseBody)
                                            .onSuccess { rgba ->
                                                setAllValuesAbsolute(rgba.r, rgba.g, rgba.b, rgba.a)
                                            }.onFailure { err ->
                                                Log.e(
                                                    ViewModelTAG,
                                                    "Could not interpret green channel response value!! Error: ${err.localizedMessage}"
                                                )
                                            }

                                    }
                                }.onFailure { err ->
                                    Log.e(
                                        ViewModelTAG,
                                        "Could not change green channel! Error: $err"
                                    )
                                }
                        }
                    }
                }
            }
            rgbaModel.value = result
        }
    }

    fun changeBlueChannel(value: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                RGBAModel().apply {
                    rgbaModel.value?.let { model ->
                        if (model.setAbsoluteBlueValue(value)) {
                            api.setBlueValue(test_endpoint, test_port, model.blueValue)
                                .onSuccess {
                                    if (it.responseCode != 200) {
                                        Log.w(
                                            ViewModelTAG,
                                            "Something went wrong when trying to change blue channel! HTTP Code: ${it.responseCode} ${it.responseBody}"
                                        )
                                    } else {
                                        RGBAModel.parseStringToModel(it.responseBody)
                                            .onSuccess { rgba ->
                                                setAllValuesAbsolute(rgba.r, rgba.g, rgba.b, rgba.a)
                                            }.onFailure { err ->
                                                Log.e(
                                                    ViewModelTAG,
                                                    "Could not interpret blue channel response value!! Error: ${err.localizedMessage}"
                                                )
                                            }

                                    }
                                }.onFailure { err ->
                                    Log.e(
                                        ViewModelTAG,
                                        "Could not change blue channel! Error: $err"
                                    )
                                }
                        }
                    }
                }
            }
            rgbaModel.value = result
        }
    }

    fun changeBrightness(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            rgbaModel.value?.let { model ->
                if (model.setAlphaValue(value)) {
                    api.setAlphaValue(test_endpoint, test_port, model.alpha).onSuccess {
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