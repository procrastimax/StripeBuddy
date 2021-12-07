package com.procrastimax.stripebuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.slider.Slider

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val redChannelSlider = view.findViewById<Slider>(R.id.slider_r)
        val greenChannelSlider = view.findViewById<Slider>(R.id.slider_g)
        val blueChannelSlider = view.findViewById<Slider>(R.id.slider_b)
        val brightnessChannelSlider = view.findViewById<Slider>(R.id.slider_brightness)

        val rgbViewModel: RGBViewModel by activityViewModels<RGBViewModel>()

        rgbViewModel.getRGBModel().observe(this, {
            // update UI
            redChannelSlider.value = it.redValue.toFloat()
            greenChannelSlider.value = it.greenValue.toFloat()
            blueChannelSlider.value = it.blueValue.toFloat()
            brightnessChannelSlider.value = it.brightness.toFloat()
        })

        redChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                rgbViewModel.changeRedChannel(slider.value.toInt())
            }
        })

        greenChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                rgbViewModel.changeGreenChannel(slider.value.toInt())
            }
        })

        blueChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                rgbViewModel.changeBlueChannel(slider.value.toInt())
            }
        })

        brightnessChannelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                rgbViewModel.changeBrightness(
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