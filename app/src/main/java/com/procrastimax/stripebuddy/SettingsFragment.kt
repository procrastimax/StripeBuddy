package com.procrastimax.stripebuddy

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val portNumberPreference : EditTextPreference? = findPreference("port")
        portNumberPreference?.setOnBindEditTextListener {editText ->
            editText.inputType = InputType.TYPE_CLASS_NUMBER
        }

        val endpointPreference : EditTextPreference? = findPreference("endpoint")
        endpointPreference?.setOnBindEditTextListener {editText ->
            editText.inputType = InputType.TYPE_TEXT_VARIATION_URI
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