package com.procrastimax.stripebuddy

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    private val states = arrayOf(
        intArrayOf(-android.R.attr.state_enabled),
    )

    // colors for graying out sliders
    private val colors = intArrayOf(
        Color.GRAY,
    )

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

        val rgbViewModel: RGBViewModel by viewModels()

        rgbViewModel.getRGBModel().observe(this) {
            // update UI
            redChannelSlider.value = it.redValue.toFloat()
            greenChannelSlider.value = it.greenValue.toFloat()
            blueChannelSlider.value = it.blueValue.toFloat()
            brightnessChannelSlider.value = it.brightness.toFloat()

            redChannelSlider.isEnabled = rgbViewModel.isReachable
            greenChannelSlider.isEnabled = rgbViewModel.isReachable
            blueChannelSlider.isEnabled = rgbViewModel.isReachable
            brightnessChannelSlider.isEnabled = rgbViewModel.isReachable

            tvRedValue.text = it.redValue.toString()
            tvGreenValue.text = it.greenValue.toString()
            tvBlueValue.text = it.blueValue.toString()
            tvBrightnessValue.text = it.brightness.toString()

            if (!rgbViewModel.isReachable) {
                Snackbar.make(view, "API is not reachable!", Snackbar.LENGTH_LONG).show()
                redChannelSlider.trackTintList = ColorStateList(states, colors)
                redChannelSlider.thumbTintList = ColorStateList(states, colors)

                greenChannelSlider.trackTintList = ColorStateList(states, colors)
                greenChannelSlider.thumbTintList = ColorStateList(states, colors)

                blueChannelSlider.trackTintList = ColorStateList(states, colors)
                blueChannelSlider.thumbTintList = ColorStateList(states, colors)

                brightnessChannelSlider.trackTintList = ColorStateList(states, colors)
                brightnessChannelSlider.thumbTintList = ColorStateList(states, colors)
            }
        }

        redChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                rgbViewModel.changeRedChannel(slider.value.toInt())
                rgbViewModel.fetchColors()
            }
        })

        greenChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                rgbViewModel.changeGreenChannel(slider.value.toInt())
                rgbViewModel.fetchColors()
            }
        })

        blueChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                rgbViewModel.changeBlueChannel(slider.value.toInt())
                rgbViewModel.fetchColors()
            }
        })

        brightnessChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                rgbViewModel.changeBrightness(
                    slider.value.toInt(),
                )
                rgbViewModel.fetchColors()
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