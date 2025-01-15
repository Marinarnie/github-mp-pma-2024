package com.example.myapp016avanocniappka

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val btnKviz: ImageButton = findViewById(R.id.btnKviz)
        val btnPuzzle: ImageButton = findViewById(R.id.btnPuzzle)
        val btnZpet: ImageButton = findViewById(R.id.btnZpet)

        // Nastavení listeneru pro tlačítko "Další"
        btnKviz.setOnClickListener {
            val intent =
                Intent(this, Kviz_activity::class.java)
            startActivity(intent)
        }

        // Nastavení listeneru pro tlačítko "Další"
        btnPuzzle.setOnClickListener {
            val intent =
                Intent(this, PuzzleActivity::class.java)
            startActivity(intent)
        }

        // Nastavení listeneru pro tlačítko "Zpět"
        btnZpet.setOnClickListener {
            // Návrat zpět na předchozí aktivitu
            finish() // Ukončí aktuální aktivitu a vrátí se na předchozí
        }
    }
}

