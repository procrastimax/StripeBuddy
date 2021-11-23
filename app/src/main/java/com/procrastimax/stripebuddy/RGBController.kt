package com.procrastimax.stripebuddy

class RGBController {

    private val api = ApiComm()
    private val rgbModel = RGBViewModel()

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

    fun getRedChannel(): Int {
        return 0
    }

    fun changeBrightness(value: Int, r: Int, g: Int, b: Int) {
        rgbModel.setBrightnessValue(value, r, g, b)
        api.setValues(rgbModel.redValue, rgbModel.greenValue, rgbModel.blueValue)
    }

}