package com.procrastimax.stripebuddy

class RGBController {

    private val api = ApiComm()
    private val rgbModel = RGBViewModel()

    fun changeRedChannel(value : Int) {
        rgbModel.setRelativeRedValue(value)
        println(rgbModel.toString())
        api.setRedValue(rgbModel.redValue)
    }

    fun changeGreenChannel(value : Int) {
        rgbModel.setRelativeGreenValue(value)
        api.setGreenValue(rgbModel.greenValue)
    }

    fun changeBlueChannel(value : Int) {
        rgbModel.setRelativeBlueValue(value)
        api.setBlueValue(rgbModel.blueValue)
    }

    fun changeBrightness(value : Int, r : Int, g : Int, b : Int) {
        rgbModel.setRelativeRedValue(r)
        rgbModel.setRelativeGreenValue(g)
        rgbModel.setRelativeBlueValue(b)
        rgbModel.setBrightnessValue(value)
        api.setValues(rgbModel.redValue, rgbModel.greenValue, rgbModel.blueValue)
    }

}