package com.example.kalendar2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kalendar2.databinding.ActivityMainBinding
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnAddEvent: Button = findViewById(R.id.btnAddEvent)

        btnAddEvent.setOnClickListener {
            // Otevření aktivity AddEventActivity
            val intent = Intent(this, AddEvent::class.java)
            startActivity(intent)
        }
    }
}