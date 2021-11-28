package com.procrastimax.stripebuddy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val ViewModelTAG = "ViewModelTAG"

class RGBViewModel() : ViewModel() {

    private val api = APIComm()

    private val rgbModel: MutableLiveData<RGBModel> by lazy() {
        MutableLiveData<RGBModel>().also {
            fetchColors()
        }
    }

    private fun fetchColors() {
        viewModelScope.launch() {
            val result = withContext(Dispatchers.IO) {
                RGBModel().apply {
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

    fun changeBrightness(value: Int, r: Int, g: Int, b: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            rgbModel.value?.let { model ->
                model.setBrightnessValue(value, r, g, b)
                api.setValues(model.redValue, model.greenValue, model.blueValue)
            }
        }
    }
}