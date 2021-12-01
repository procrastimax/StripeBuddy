package com.procrastimax.stripebuddy

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

    private val rgbModel: MutableLiveData<RGBModel> by lazy {
        MutableLiveData<RGBModel>().also {
            fetchColors()
        }
    }

    private fun fetchColors() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                RGBModel().apply {
                    // TODO: add check for health of API, if not available the app exits
                    val res = api.getValues()
                    setAbsoluteRedValue(res.first)
                    setAbsoluteGreenValue(res.second)
                    setAbsoluteBlueValue(res.third)
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
                model.setAbsoluteRedValue(value)
                api.setRedValue(model.redValue)
            }
        }
    }

    fun changeGreenChannel(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            rgbModel.value?.let { model ->
                model.setAbsoluteGreenValue(value)
                api.setGreenValue(model.greenValue)
            }
        }
    }

    fun changeBlueChannel(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            rgbModel.value?.let { model ->
                model.setAbsoluteBlueValue(value)
                api.setBlueValue(model.blueValue)
            }
        }
    }

    fun changeBrightness(value: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                RGBModel().apply {
                    setBrightnessValue(value)
                    val res = api.setBrightnessValue(value)
                    setAbsoluteRedValue(res.first)
                    setAbsoluteGreenValue(res.second)
                    setAbsoluteBlueValue(res.third)
                }
            }
            rgbModel.value = result
        }
    }
}