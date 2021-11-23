package com.procrastimax.stripebuddy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RGBViewModel : ViewModel() {

    private val api = APIComm()
    private val rgbModel = RGBModel()

    private val r : MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().also {
            api.getRedValue()
        }
    }

    fun changeRedChannel(value: Int) {
        rgbModel.setAbsoluteRedValue(value)
        println(rgbModel.toString())
        api.setRedValue(rgbModel.redValue)
    }

    fun changeGreenChannel(value: Int) {
        rgbModel.setAbsoluteGreenValue(value)
        api.setGreenValue(rgbModel.greenValue)
    }

    fun changeBlueChannel(value: Int) {
        rgbModel.setAbsoluteBlueValue(value)
        api.setBlueValue(rgbModel.blueValue)
    }

    fun getRedChannel(): LiveData<Int> {
        return r
    }

    fun changeBrightness(value: Int, r: Int, g: Int, b: Int) {
        rgbModel.setBrightnessValue(value, r, g, b)
        api.setValues(rgbModel.redValue, rgbModel.greenValue, rgbModel.blueValue)
    }

}