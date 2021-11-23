package com.procrastimax.stripebuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.slider.Slider

/**
 * A simple [Fragment] subclass.
 * Use the [RGBSliderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RGBSliderFragment(val controller: RGBController) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_r_g_b_slider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val redChannelSlider = view.findViewById<Slider>(R.id.slider_r)
        val greenChannelSlider = view.findViewById<Slider>(R.id.slider_g)
        val blueChannelSlider = view.findViewById<Slider>(R.id.slider_b)
        val brightnessChannelSlider = view.findViewById<Slider>(R.id.slider_brightness)

        redChannelSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                controller.changeRedChannel(value.toInt())
            }
        }
        redChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                // check current value of RGB and slider according
            }
        })


        greenChannelSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                controller.changeGreenChannel(value.toInt())
            }
        }
        greenChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                // check current value of RGB and slider according
            }
        })

        blueChannelSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                controller.changeBlueChannel(value.toInt())
            }
        }
        blueChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                // check current value of RGB and slider according
            }
        })

        brightnessChannelSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                controller.changeBrightness(
                    value.toInt(),
                    redChannelSlider.value.toInt(),
                    greenChannelSlider.value.toInt(),
                    blueChannelSlider.value.toInt()
                )
            }
        }
        brightnessChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                // check current value of RGB and slider according
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
        fun newInstance(controller: RGBController) =
            RGBSliderFragment(controller)
    }
}