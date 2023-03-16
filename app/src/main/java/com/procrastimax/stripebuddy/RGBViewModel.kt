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

    var isreachable: Boolean = true

    private val rgbaModel: MutableLiveData<RGBAModel> by lazy {
        MutableLiveData<RGBAModel>()
    }

    /**
     * Retrieves current color values from the API (r,g,b,brightness) and updates the model accordingly
     * If the API is not reachable, a specific flag is set.
     */
    fun fetchColors(endpoint: String, port: Int) {
        Log.d("RGBViewModel", "--> Fetch is called!!! <--")
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                RGBAModel().apply {
                    api.getValues(endpoint, port).onSuccess { valueRes ->
                        if (valueRes.responseCode == 200) {
                            if (valueRes.responseBody == "") {
                                Log.w(
                                    ViewModelTAG,
                                    "Received HTTP response with Code: ${valueRes.responseCode}, but empty response body!"
                                )
                            } else {
                                RGBAModel.parseStringToModel(valueRes.responseBody)
                                    .onSuccess { rgba ->
                                        isreachable = true
                                        this.setAllValuesAbsolute(rgba.r, rgba.g, rgba.b, rgba.a)

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
                        isreachable = false
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

    fun changeRedChannel(endpoint: String, port: Int, value: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                RGBAModel().apply {
                    rgbaModel.value?.let { model ->
                        if (model.setAbsoluteRedValue(value)) {
                            api.setRedValue(endpoint, port, model.redValue)
                                .onSuccess {
                                    if (it.responseCode != 200) {
                                        Log.w(
                                            ViewModelTAG,
                                            "Something went wrong when trying to change red channel! HTTP Code: ${it.responseCode} ${it.responseBody}"
                                        )
                                    } else {
                                        RGBAModel.parseStringToModel(it.responseBody)
                                            .onSuccess { rgba ->
                                                isreachable = true
                                                this.setAllValuesAbsolute(
                                                    rgba.r,
                                                    rgba.g,
                                                    rgba.b,
                                                    rgba.a
                                                )
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
                                    isreachable = false
                                }
                        }
                    }
                }
            }
            rgbaModel.value = result
        }
    }

    fun changeGreenChannel(endpoint: String, port: Int, value: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                RGBAModel().apply {
                    rgbaModel.value?.let { model ->
                        if (model.setAbsoluteGreenValue(value)) {
                            api.setGreenValue(endpoint, port, model.greenValue)
                                .onSuccess {
                                    if (it.responseCode != 200) {
                                        Log.w(
                                            ViewModelTAG,
                                            "Something went wrong when trying to change green channel! HTTP Code: ${it.responseCode} ${it.responseBody}"
                                        )
                                    } else {
                                        RGBAModel.parseStringToModel(it.responseBody)
                                            .onSuccess { rgba ->
                                                isreachable = true
                                                this.setAllValuesAbsolute(
                                                    rgba.r,
                                                    rgba.g,
                                                    rgba.b,
                                                    rgba.a
                                                )
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
                                    isreachable = false
                                }
                        }
                    }
                }
            }
            rgbaModel.value = result
        }
    }

    fun changeBlueChannel(endpoint: String, port: Int, value: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                RGBAModel().apply {
                    rgbaModel.value?.let { model ->
                        if (model.setAbsoluteBlueValue(value)) {
                            api.setBlueValue(endpoint, port, model.blueValue)
                                .onSuccess {
                                    if (it.responseCode != 200) {
                                        Log.w(
                                            ViewModelTAG,
                                            "Something went wrong when trying to change blue channel! HTTP Code: ${it.responseCode} ${it.responseBody}"
                                        )
                                    } else {
                                        RGBAModel.parseStringToModel(it.responseBody)
                                            .onSuccess { rgba ->
                                                isreachable = true
                                                this.setAllValuesAbsolute(
                                                    rgba.r,
                                                    rgba.g,
                                                    rgba.b,
                                                    rgba.a
                                                )
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
                                    isreachable = false
                                }
                        }
                    }
                }
            }
            rgbaModel.value = result
        }
    }

    fun changeBrightness(endpoint: String, port: Int, value: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                RGBAModel().apply {
                    rgbaModel.value?.let { model ->
                        if (model.setAlphaValue(value)) {
                            api.setAlphaValue(endpoint, port, model.alpha)
                                .onSuccess {
                                    if (it.responseCode != 200) {
                                        Log.w(
                                            ViewModelTAG,
                                            "Something went wrong when trying to change alpha channel! HTTP Code: ${it.responseCode} ${it.responseBody}"
                                        )
                                    } else {
                                        RGBAModel.parseStringToModel(it.responseBody)
                                            .onSuccess { rgba ->
                                                isreachable = true
                                                this.setAllValuesAbsolute(
                                                    rgba.r,
                                                    rgba.g,
                                                    rgba.b,
                                                    rgba.a
                                                )
                                            }.onFailure { err ->
                                                Log.e(
                                                    ViewModelTAG,
                                                    "Could not interpret alpha channel response value!! Error: ${err.localizedMessage}"
                                                )
                                            }

                                    }
                                }.onFailure { err ->
                                    Log.e(
                                        ViewModelTAG,
                                        "Could not change alpha channel! Error: $err"
                                    )
                                    isreachable = false
                                }
                        }
                    }
                }
            }
            rgbaModel.value = result
        }
    }
}