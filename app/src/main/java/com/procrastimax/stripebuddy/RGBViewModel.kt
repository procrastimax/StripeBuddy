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

    private fun fetchColors() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                RGBModel().apply {
                    isReachable = api.checkHealth()
                    if (isReachable) {
                        val res = api.getValues()
                        setAbsoluteRedValue(res.r)
                        setAbsoluteGreenValue(res.g)
                        setAbsoluteBlueValue(res.b)
                        setBrightnessValue(res.brightness)
                    } else {
                        Log.w(ViewModelTAG, "API is not reachable!")
                        setAbsoluteRedValue(0)
                        setAbsoluteGreenValue(0)
                        setAbsoluteBlueValue(0)
                        setBrightnessValue(100)
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