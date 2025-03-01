package com.example.decathlon

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MesicActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mesic)

        // Seznam ID tlačítek pro měsíce
        val mesicButtons = listOf(
            R.id.btnLeden, R.id.btnUnor, R.id.btnBrezen, R.id.btnDuben,
            R.id.btnKveten, R.id.btnCerven, R.id.btnCervenec, R.id.btnSrpen,
            R.id.btnZari, R.id.btnRijen, R.id.btnListopad, R.id.btnProsinec
        )

        // Nastavení onClickListener pro každé tlačítko
        for (buttonId in mesicButtons) {
            findViewById<Button>(buttonId).setOnClickListener(this)
        }

        // 🔙 Tlačítko zpět
        findViewById<ImageButton>(R.id.btnZpetMesice).setOnClickListener {
            finish()
        }
    }

    override fun onClick(v: View?) {
        // Přesměrování do EidActivity bez předávání dat
        val intent = Intent(this, EidActivity::class.java)
        startActivity(intent)
    }
}
