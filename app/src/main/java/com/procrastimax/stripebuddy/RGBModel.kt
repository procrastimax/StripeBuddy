package com.procrastimax.stripebuddy

data class RGBModel(val r: Int = 0, val g: Int = 0, val b: Int = 0, val br: Int = 100) {

    var redValue: Int = 0
        private set

    var greenValue: Int = 0
        private set

    var blueValue: Int = 0
        private set

    var brightness: Int = 100
        private set

    init {
        setAbsoluteRedValue(r)
        setAbsoluteGreenValue(g)
        setAbsoluteBlueValue(b)
        setBrightnessValue(br)
    }

    /**
     * Sets the red channel to its absolute value (0-255)
     * @param r : Int (0-255)
     * @return Boolean - whether the input param is within the valid range
     */
    fun setAbsoluteRedValue(r: Int): Boolean {
        return if (checkAbsoluteValue(r)) {
            this.redValue = r
            true
        } else {
            false
        }
    }

    /**
     * Sets the green channel to its absolute value
     * @param g : Int (0-255)
     * @return Boolean - whether the input param is within the valid range
     */
    fun setAbsoluteGreenValue(g: Int): Boolean {
        return if (checkAbsoluteValue(g)) {
            this.greenValue = g
            true
        } else {
            false
        }
    }

    /**
     * Sets the blue channel to its absolute value
     * @param b : Int (0-255)
     * @return Boolean - whether the input param is within the valid range
     */
    fun setAbsoluteBlueValue(b: Int): Boolean {
        return if (checkAbsoluteValue(b)) {
            this.blueValue = b
            true
        } else {
            false
        }
    }

    /**
     * Sets the all channels to the absolute value
     * @param value : Int (0-255)
     * @return Boolean - whether the input param is within the valid range
     */
    fun setAllValuesAbsolute(value: Int): Boolean {
        return if (checkAbsoluteValue(value)) {
            this.redValue = value
            this.greenValue = value
            this.blueValue = value
            true
        } else {
            false
        }
    }

    /**
     * Sets the brightness value and scales all other channels according to the set brightness value
     * @param brightness : Int (0-100)
     * @return Boolean - whether the input param is within the valid range
     **/
    fun setBrightnessValue(brightness: Int): Boolean {
        return if (checkRelativeValue(brightness)) {
            this.brightness = brightness
            true
        } else {
            false
        }
    }

    private fun checkAbsoluteValue(value: Int): Boolean {
        return (value in MIN_ABSOLUTE_VALUE..MAX_ABSOLUTE_VALUE)
    }

    private fun checkRelativeValue(value: Int): Boolean {
        return (value in 0..100)
    }

    override fun toString(): String {
        return "RGBViewModel(red_value=$redValue, green_value=$greenValue, blue_value=$blueValue, brightness=$brightness)"
    }

    companion object {
        const val MIN_ABSOLUTE_VALUE: Int = 0
        const val MAX_ABSOLUTE_VALUE: Int = 255

        fun parseStringToModel(inputStr: String): Result<RGBModel> {
            val arr: List<String> = inputStr.split(",")
            return if (arr.size == 4) {
                try {
                    Result.success(
                        RGBModel(
                            r = arr[0].toInt(),
                            g = arr[1].toInt(),
                            b = arr[2].toInt(),
                            br = arr[3].toInt()
                        )
                    )
                } catch (e: NumberFormatException) {
                    Result.failure(Exception("Could not properly format RGB and brightness values! Received string: $inputStr"))
                }
            } else {
                Result.failure(Exception("Did not receive RGB and brightness value in proper format: R,G,B,Brightness! Received string: $inputStr"))
            }
        }
    }
}