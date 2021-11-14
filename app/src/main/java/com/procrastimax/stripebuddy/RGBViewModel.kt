package com.procrastimax.stripebuddy

class RGBViewModel {
    private var red_value: Int = 0
    private var green_value: Int = 0
    private var blue_value: Int = 0

    fun setRedValue(value: Int): Boolean {
        return if (checkValue(value)) {
            this.red_value = value
            true
        } else {
            false
        }
    }

    fun setGreenValue(value: Int): Boolean {
        return if (checkValue(value)) {
            this.green_value = value
            true
        } else {
            false
        }
    }

    fun setBlueValue(value: Int): Boolean {
        return if (checkValue(value)) {
            this.blue_value = value
            true
        } else {
            false
        }
    }

    fun setValues(r: Int, g: Int, b: Int): Boolean {
        return if (checkValue(r) && checkValue(g) && checkValue(b)) {
            this.red_value = r
            this.green_value = g
            this.blue_value = b
            true
        } else {
            false
        }
    }

    fun setAllValues(value: Int): Boolean {
        return if (checkValue(value)) {
            this.red_value = value
            this.green_value = value
            this.blue_value = value
            true
        } else {
            false
        }
    }

    private fun checkValue(value: Int): Boolean {
        return (value in MIN_VALUE..MAX_VALUE)
    }

    fun getRedValue(): Int {
        return this.red_value
    }

    fun getGreenValue(): Int {
        return this.green_value
    }

    fun getBlueValue(): Int {
        return this.blue_value
    }

    override fun toString(): String {
        return "RGBViewModel(red_value=$red_value, green_value=$green_value, blue_value=$blue_value)"
    }

    companion object {
        const val MAX_VALUE: Int = 255
        const val MIN_VALUE: Int = 0
    }
}