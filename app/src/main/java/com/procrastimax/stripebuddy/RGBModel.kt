package com.procrastimax.stripebuddy

data class RGBAModel(val r: Int = 0, val g: Int = 0, val b: Int = 0, val a: Int = 255) {

    var redValue: Int = 0
        private set

    var greenValue: Int = 0
        private set

    var blueValue: Int = 0
        private set

    var alpha: Int = 100
        private set

    init {
        setAbsoluteRedValue(r)
        setAbsoluteGreenValue(g)
        setAbsoluteBlueValue(b)
        setAlphaValue(a)
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
     * Sets the all channels (RGBA) to the absolute value
     * @param r,g,b,a : Int (0-255)
     * @return Boolean - whether the inputs params are within the valid range
     */
    fun setAllValuesAbsolute(r: Int, g: Int, b: Int, a: Int): Boolean {
        return if (checkAbsoluteValue(r) && checkAbsoluteValue(g) && checkAbsoluteValue(b) && checkAbsoluteValue(
                a
            )
        ) {
            this.redValue = r
            this.greenValue = g
            this.blueValue = b
            this.alpha = a
            true
        } else {
            false
        }
    }

    /**
     * Sets the brightness value and scales all other channels according to the set brightness value
     * @param alpha : Int (0-255)
     * @return Boolean - whether the input param is within the valid range
     **/
    fun setAlphaValue(brightness: Int): Boolean {
        return if (checkAbsoluteValue(brightness)) {
            this.alpha = brightness
            true
        } else {
            false
        }
    }

    private fun checkAbsoluteValue(value: Int): Boolean {
        return (value in MIN_ABSOLUTE_VALUE..MAX_ABSOLUTE_VALUE)
    }

    override fun toString(): String {
        return "RGBViewModel(red_value=$redValue, green_value=$greenValue, blue_value=$blueValue, brightness=$alpha)"
    }

    companion object {
        const val MIN_ABSOLUTE_VALUE: Int = 0
        const val MAX_ABSOLUTE_VALUE: Int = 255

        fun parseStringToModel(inputStr: String): Result<RGBAModel> {
            val arr: List<String> = inputStr.split(",")
            return if (arr.size == 4) {
                try {
                    Result.success(
                        RGBAModel(
                            r = arr[0].toInt(),
                            g = arr[1].toInt(),
                            b = arr[2].toInt(),
                            a = arr[3].toInt()
                        )
                    )
                } catch (e: NumberFormatException) {
                    Result.failure(Exception("Could not properly format RGBA values! Received string: $inputStr"))
                }
            } else {
                Result.failure(Exception("Did not receive RGBA value in proper format: R,G,B,A! Received string: $inputStr"))
            }
        }
    }
}