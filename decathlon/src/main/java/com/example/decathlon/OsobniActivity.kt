package com.example.decathlon

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class OsobniActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_osobni)

        // Získání ID zaměstnance z intentu
        val zamestnanecId = intent.getIntExtra("ZAMESTNANEC_ID", -1)

        // Seznam zaměstnanců (zatím lokálně, později přes databázi)
        val seznamZamestnancu = listOf(
            Zamestnanec(1, "Jan Novák"),
            Zamestnanec(2, "Petr Svoboda"),
            Zamestnanec(3, "Anna Dvořáková")
        )

        // Najdeme zaměstnance podle ID
        val zamestnanec = seznamZamestnancu.find { it.id == zamestnanecId }

        // Nastavení jména v TextView
        val tvJmenoPrijmeni = findViewById<TextView>(R.id.tvJménoPříjmení)
        tvJmenoPrijmeni.text = zamestnanec?.jmeno ?: "Neznámý zaměstnanec"

        // Tlačítko zpět
        val btnZpet = findViewById<ImageButton>(R.id.btnZpet4)
        btnZpet.setOnClickListener {
            finish()
        }

        // 📌 Kliknutí na EidFormular přesměruje na RokActivity
        val eidFormular = findViewById<LinearLayout>(R.id.EidFormular)
        eidFormular.setOnClickListener {
            val intent = Intent(this, RokActivity::class.java)
            intent.putExtra("ZAMESTNANEC_ID", zamestnanecId) // Předání ID zaměstnance
            startActivity(intent)
        }
    }
}
