package com.example.decathlon

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class VyberOddeleniActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vyber_oddeleni)

        // Seznam ID ImageView ikon pro oddělení
        val oddeleniList = mapOf(
            R.id.ivWD to "Welcome Desk",
            R.id.ivTuristika to "Turistika",
            R.id.ivVoda to "Voda/wedze",
            R.id.ivServis to "Servis",
            R.id.ivCyklo to "Cyklistika",
            R.id.ivBeh to "Běh",
            R.id.ivFitness to "Fitness",
            R.id.ivTymovky to "Kolektivky"
        )

        // Nastavení onClickListener pro každé oddělení
        oddeleniList.forEach { (imageViewId, oddeleniNazev) ->
            findViewById<ImageView>(imageViewId).setOnClickListener {
                val intent = Intent(this, OddeleniActivity::class.java)
                intent.putExtra("ODDELENI_NAZEV", oddeleniNazev)
                startActivity(intent)
            }
        }
    }
}
