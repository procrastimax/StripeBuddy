package com.procrastimax.stripebuddy

import android.os.Bundle
import android.text.InputType
import android.webkit.URLUtil
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val portNumberPreference: EditTextPreference? = findPreference("port")
        portNumberPreference?.setOnBindEditTextListener { editText ->
            editText.inputType = InputType.TYPE_CLASS_NUMBER
        }

        val endpointPreference: EditTextPreference? = findPreference("endpoint")
        endpointPreference?.setOnBindEditTextListener { editText ->
            editText.inputType = InputType.TYPE_TEXT_VARIATION_URI
        }

        endpointPreference?.setOnPreferenceChangeListener { _, newValue ->
            if (URLUtil.isValidUrl(newValue.toString())) {
                true
            } else {
                Toast.makeText(
                    context,
                    "$newValue is not a valid API Endpoint URL/ IP Adress!",
                    Toast.LENGTH_LONG
                ).show()
                false
            }
        }

        portNumberPreference?.setOnPreferenceChangeListener { _, newValue ->
            val portNumber: Int? = try {
                newValue.toString().toInt()
            } catch (e: NumberFormatException) {
                null
            }

            portNumber?.run {
                if (this in 1..65535) {
                    true
                } else {
                    Toast.makeText(
                        context,
                        "$newValue is not a valid port number!",
                        Toast.LENGTH_LONG
                    ).show()
                    false
                }
            } ?: false
        }
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
            SettingsFragment()
    }
}