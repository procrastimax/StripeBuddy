package com.procrastimax.stripebuddy

import kotlin.math.roundToInt

// TODO: Implement proper Error Handling with: https://kotlin.christmas/2019/17/ Result Type
// TODO: apply brightness factor to all relative RGB values

const val ModelTAG = "RGBModel"

class RGBModel {
    /**
     *  redValue controls the red channel intensity (0-255)
     *  it has a private setter, so the value is only set via the proper functions (e.g. setRedValue())
     **/
    var redValue: Int = 0
        private set

    /**
     * greenValue controls the green channel intensity (0-255)
     * it has a private setter, so the value is only set via the proper functions (e.g. setGreenValue())
     */
    var greenValue: Int = 0
        private set

    /**
     * blueValue controls the blue channel intensity (0-255)
     * it has a private setter, so the value is only set via the proper functions (e.g. setBlueValue())
     */
    var blueValue: Int = 0
        private set

    /**
     * brightness controls the brightness of each channel (0-100)
     * When changed, the brightness factor scales all the RGB-Channels up or down, which corresponds to the brightness
     */
    var brightness: Int = 100
        private set

    /**
     * Sets the red channel to its absolute value (0-255)
     * @param r : Int (0-255)
     * @return Boolean - whether the input param is within the valid range
     */
    fun setAbsoluteRedValue(r: Int): Boolean {
        return if (checkAbsoluteValue(r)) {
            this.redValue = (r * (brightness.toFloat() / 100.0)).roundToInt()
            true
        } else {
            false
        }
    }

    /*fun setRelativeRedValue(r: Int): Boolean {
        return if (checkRelativeValue(r)) {
            this.redValue = convertRelativeToAbsoluteValue(r)
            Log.d(ModelTAG, "${r} - ${this.redValue} - ${convertRelativeToAbsoluteValue(r)} ")
            true
        } else {
            false
        }
    }*/

    /**
     * Sets the green channel to its absolute value
     * @param g : Int (0-255)
     * @return Boolean - whether the input param is within the valid range
     */
    fun setAbsoluteGreenValue(g: Int): Boolean {
        return if (checkAbsoluteValue(g)) {
            this.greenValue = (g * (brightness.toFloat() / 100.0)).roundToInt()
            true
        } else {
            false
        }
    }

    /*fun setRelativeGreenValue(g: Int): Boolean {
        return if (checkRelativeValue(g)) {
            this.greenValue = convertRelativeToAbsoluteValue(g)
            true
        } else {
            false
        }
    }*/

    /**
     * Sets the blue channel to its absolute value
     * @param b : Int (0-255)
     * @return Boolean - whether the input param is within the valid range
     */
    fun setAbsoluteBlueValue(b: Int): Boolean {
        return if (checkAbsoluteValue(b)) {
            this.blueValue = (b * (brightness.toFloat() / 100.0)).roundToInt()
            true
        } else {
            false
        }
    }

    /*fun setRelativeBlueValue(b: Int): Boolean {
        return if (checkRelativeValue(b)) {
            this.blueValue = convertRelativeToAbsoluteValue(b)
            true
        } else {
            false
        }
    }*/

    /**
     * Sets the all channels to their absolute values
     * @param r : Int (0-255)
     * @param g : Int (0-255)
     * @param b : Int (0-255)
     * @return Boolean - whether the input param is within the valid range
     */
    fun setAbsoluteValues(r: Int, g: Int, b: Int): Boolean {
        return if (checkAbsoluteValue(r) && checkAbsoluteValue(g) && checkAbsoluteValue(b)) {
            this.redValue = (r * (brightness.toFloat() / 100.0)).roundToInt()
            this.greenValue = (g * (brightness.toFloat() / 100.0)).roundToInt()
            this.blueValue = (b * (brightness.toFloat() / 100.0)).roundToInt()
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
            val calcVal = (value * (brightness.toFloat() / 100.0)).roundToInt()
            this.redValue = calcVal
            this.greenValue = calcVal
            this.blueValue = calcVal
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
    fun setBrightnessValue(brightness: Int, r: Int, g: Int, b: Int): Boolean {
        return if (checkRelativeValue(brightness)) {
            this.brightness = brightness
            this.redValue = (r * (brightness.toFloat() / 100.0)).roundToInt()
            this.greenValue = (g * (brightness.toFloat() / 100.0)).roundToInt()
            this.blueValue = (b * (brightness.toFloat() / 100.0)).roundToInt()
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

    private fun convertRelativeToAbsoluteValue(value: Int): Int {
        return if (checkRelativeValue(value)) {
            (value.toFloat() / 100.0 * MAX_ABSOLUTE_VALUE.toFloat()).roundToInt()
        } else {
            0
        }
    }

    override fun toString(): String {
        return "RGBViewModel(red_value=$redValue, green_value=$greenValue, blue_value=$blueValue, brightness=$brightness)"
    }

    companion object {
        const val MIN_ABSOLUTE_VALUE: Int = 0
        const val MAX_ABSOLUTE_VALUE: Int = 255
    }
}