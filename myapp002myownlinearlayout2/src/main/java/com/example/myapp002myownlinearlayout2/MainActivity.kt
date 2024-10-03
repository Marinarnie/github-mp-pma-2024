package com.example.myapp002myownlinearlayout2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    private lateinit var sirka: EditText
    private lateinit var delka: EditText
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sirka = findViewById(R.id.etsirka)
        delka = findViewById(R.id.etdelka)
        textView = findViewById(R.id.etText)

        val sendButton = findViewById<Button>(R.id.etbtn)
        val deleteButton = findViewById<Button>(R.id.etbtn2)

        sendButton.setOnClickListener {
            val sirka: String = sirka.getText().toString()
            val delka: String = delka.getText().toString()

            val result = sirka.toDouble() * delka.toDouble()
            val displayText = """
               ${textView.text}: $result"""

                
            textView.text = displayText
        }

        deleteButton.setOnClickListener {
            textView.text = ""
            sirka.text.clear()
            delka.text.clear()
        }
    }
}