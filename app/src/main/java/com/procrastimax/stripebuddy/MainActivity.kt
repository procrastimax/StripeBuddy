package com.procrastimax.stripebuddy

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : Activity() {

    val api = ApiComm()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_rgb)

        val btn = findViewById<Button>(R.id.button)
        val edit_r = findViewById<EditText>(R.id.edit_r_value)
        val edit_g = findViewById<EditText>(R.id.edit_g_value)
        val edit_b = findViewById<EditText>(R.id.edit_b_value)

        btn.setOnClickListener {
            api.setValues(edit_r.text.toString().toInt(), edit_g.text.toString().toInt(), edit_b.text.toString().toInt())
        }

        if (api.checkHealth()){
            Toast.makeText(this, "API is alive!", Toast.LENGTH_SHORT).show()
        }
    }
}