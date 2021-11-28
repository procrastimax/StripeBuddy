package com.procrastimax.stripebuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels

/**
 * A simple [Fragment] subclass.
 * Use the [RGBExactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RGBExactFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_r_g_b_exact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val edit_r = view.findViewById<EditText>(R.id.edit_r_value)
        val edit_g = view.findViewById<EditText>(R.id.edit_g_value)
        val edit_b = view.findViewById<EditText>(R.id.edit_b_value)

        val send_btn = view.findViewById<Button>(R.id.button)

        val rgbViewModel : RGBViewModel by activityViewModels<RGBViewModel>()

        rgbViewModel.getRGBModel().observe(viewLifecycleOwner) {
            // update UI
            edit_r.setText(it.redValue.toString())
            edit_g.setText(it.greenValue.toString())
            edit_b.setText(it.blueValue.toString())
        }

        send_btn.setOnClickListener {
            rgbViewModel.changeRedChannel(edit_r.text.toString().toInt())
            rgbViewModel.changeGreenChannel(edit_g.text.toString().toInt())
            rgbViewModel.changeBlueChannel(edit_b.text.toString().toInt())
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RGBExactFragment.
         */
        @JvmStatic
        fun newInstance() =
            RGBExactFragment()
    }
}