package com.example.decathlon

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class OddeleniActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oddeleni)

        // Získání názvu oddělení z Intentu
        val oddeleniNazev = intent.getStringExtra("ODDELENI_NAZEV")
//        val oddeleniNazev = intent.getStringExtra("N")

        // Zobrazení názvu oddělení v TextView
        findViewById<TextView>(R.id.tvNázevOddělení).text = oddeleniNazev

        // 🏠 Tlačítko ZPĚT (btnZpet2) - Zavře aktivitu
        findViewById<ImageButton>(R.id.btnZpet2).setOnClickListener {
            finish() // Zavře aktuální aktivitu a vrátí se zpět
        }

        // 📌 Kliknutí na celý layout (btnTým_oddělení) - Přesměrování na TeamActivity
        findViewById<LinearLayout>(R.id.btnTým_oddělení).setOnClickListener {
            val intent = Intent(this, TeamActivity::class.java)
            startActivity(intent)
        }
    }
}
