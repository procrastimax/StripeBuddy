package com.procrastimax.stripebuddy

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [RGBSliderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RGBSliderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_r_g_b_slider, container, false)
    }

    // states for graying out sliders
    private val state = arrayOf(
        intArrayOf(-android.R.attr.state_enabled),
    )

    // colors for graying out sliders
    private val color = intArrayOf(
        Color.GRAY
    )

    val rgbViewModel: RGBViewModel by viewModels()


    var apiPort: Int = 80
    var apiEndpoint: String = "stripe.local"

    private fun updateApiSettings() {
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context!!)
        apiPort = sharedPreference.getString("port", "443").toString().toInt()
        apiEndpoint = sharedPreference.getString("endpoint", "stripe.local").toString()
    }

    override fun onPause() {
        super.onPause()
        rgbViewModel.isreachable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val redChannelSlider = view.findViewById<Slider>(R.id.slider_r)
        val greenChannelSlider = view.findViewById<Slider>(R.id.slider_g)
        val blueChannelSlider = view.findViewById<Slider>(R.id.slider_b)
        val brightnessChannelSlider = view.findViewById<Slider>(R.id.slider_brightness)

        val tvRedValue = view.findViewById<TextView>(R.id.tv_val_r_channel)
        val tvGreenValue = view.findViewById<TextView>(R.id.tv_val_g_channel)
        val tvBlueValue = view.findViewById<TextView>(R.id.tv_val_b_channel)
        val tvBrightnessValue = view.findViewById<TextView>(R.id.tv_val_brightness_channel)

        updateApiSettings()
        rgbViewModel.fetchColors(apiEndpoint, apiPort)


        rgbViewModel.getRGBModel().observe(this) {
            Log.d("SliderFragment", "change observed: $it")

            // update UI
            redChannelSlider.value = it.redValue.toFloat()
            greenChannelSlider.value = it.greenValue.toFloat()
            blueChannelSlider.value = it.blueValue.toFloat()
            brightnessChannelSlider.value = it.alpha.toFloat()

            redChannelSlider.isEnabled = rgbViewModel.isreachable
            greenChannelSlider.isEnabled = rgbViewModel.isreachable
            blueChannelSlider.isEnabled = rgbViewModel.isreachable
            brightnessChannelSlider.isEnabled = rgbViewModel.isreachable

            tvRedValue.text = it.redValue.toString()
            tvGreenValue.text = it.greenValue.toString()
            tvBlueValue.text = it.blueValue.toString()
            tvBrightnessValue.text = it.alpha.toString()

            if (!rgbViewModel.isreachable) {
                Snackbar.make(
                    activity!!.findViewById(R.id.activity_parent),
                    "API is not reachable!",
                    Snackbar.LENGTH_LONG
                ).show()
                redChannelSlider.trackTintList = ColorStateList(state, color)
                redChannelSlider.thumbTintList = ColorStateList(state, color)

                greenChannelSlider.trackTintList = ColorStateList(state, color)
                greenChannelSlider.thumbTintList = ColorStateList(state, color)

                blueChannelSlider.trackTintList = ColorStateList(state, color)
                blueChannelSlider.thumbTintList = ColorStateList(state, color)

                brightnessChannelSlider.trackTintList = ColorStateList(state, color)
                brightnessChannelSlider.thumbTintList = ColorStateList(state, color)
            }
        }


        redChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            @SuppressLint("RestrictedApi")
            override fun onStartTrackingTouch(slider: Slider) {
            }

            @SuppressLint("RestrictedApi")
            override fun onStopTrackingTouch(slider: Slider) {
                rgbViewModel.changeRedChannel(apiEndpoint, apiPort, slider.value.toInt())
            }
        })

        greenChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            @SuppressLint("RestrictedApi")
            override fun onStartTrackingTouch(slider: Slider) {
            }

            @SuppressLint("RestrictedApi")
            override fun onStopTrackingTouch(slider: Slider) {
                rgbViewModel.changeGreenChannel(apiEndpoint, apiPort, slider.value.toInt())
            }
        })

        blueChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            @SuppressLint("RestrictedApi")
            override fun onStartTrackingTouch(slider: Slider) {
            }

            @SuppressLint("RestrictedApi")
            override fun onStopTrackingTouch(slider: Slider) {
                rgbViewModel.changeBlueChannel(apiEndpoint, apiPort, slider.value.toInt())
            }
        })

        brightnessChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            @SuppressLint("RestrictedApi")
            override fun onStartTrackingTouch(slider: Slider) {
            }

            @SuppressLint("RestrictedApi")
            override fun onStopTrackingTouch(slider: Slider) {
                rgbViewModel.changeBrightness(
                    apiEndpoint, apiPort,
                    slider.value.toInt(),
                )
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RGBSliderFragment.
         */
        @JvmStatic
        fun newInstance() =
            RGBSliderFragment()
    }
}