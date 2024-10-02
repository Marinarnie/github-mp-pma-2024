package com.example.myapp01linearlayout

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val etbtn = findViewById<R.id.et>()
        etbtn.setOnClickListener {
            calculateArea()
        }

        etbtn2.setOnClickListener {
            clearInputs()
        }
    }

    private fun calculateArea() {
        val widthInput = etsirka.text.toString()
        val lengthInput = etdelka.text.toString()

        if (widthInput.isNotEmpty() && lengthInput.isNotEmpty()) {
            val width = widthInput.toDouble()
            val length = lengthInput.toDouble()
            val area = width * length

            etText.text = "Plocha: $area m²"
        } else {
            etText.text = "Zadejte obě hodnoty."
        }
    }

    private fun clearInputs() {
        etsirka.text.clear()
        etdelka.text.clear()
        etText.text = "Plocha v m²"
    }
}