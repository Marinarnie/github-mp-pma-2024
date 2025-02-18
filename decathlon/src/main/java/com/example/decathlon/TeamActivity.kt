package com.example.decathlon

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TeamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        val container = findViewById<LinearLayout>(R.id.containter_zamestnanců)
        container.orientation = LinearLayout.VERTICAL // Každý řádek bude nový LinearLayout

        // Simulace seznamu zaměstnanců v oddělení
        val seznamZamestnancu = listOf(
            Zamestnanec(1, "Jan Novák"),
            Zamestnanec(2, "Petr Svoboda"),
            Zamestnanec(3, "Anna Dvořáková"),
            Zamestnanec(4, "Klára Veselá"),
            Zamestnanec(5, "Tomáš Doležal"),
            Zamestnanec(6, "Lucie Králová")
        )

        var rowLayout: LinearLayout? = null

        seznamZamestnancu.forEachIndexed { index, zamestnanec ->
            // Každé 3 zaměstnance vložíme do jednoho řádku
            if (index % 3 == 0) {
                rowLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    gravity = Gravity.CENTER
                }
                container.addView(rowLayout)
            }

            val employeeLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            }

            val imageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(180, 180) // Větší obrázek pro viditelnost
                setImageResource(R.drawable.fotka)  // Defaultní obrázek
                scaleType = ImageView.ScaleType.CENTER_CROP
                isClickable = true

                val resId = resources.getIdentifier("foto_${zamestnanec.id}", "drawable", packageName)
                if (resId != 0) {
                    setImageResource(resId)
                }

                setOnClickListener {
                    val intent = Intent(this@TeamActivity, OsobniActivity::class.java)
                    intent.putExtra("ZAMESTNANEC_ID", zamestnanec.id)
                    startActivity(intent)
                }
            }

            val textView = TextView(this).apply {
                text = zamestnanec.jmeno
                textSize = 16f
                gravity = Gravity.CENTER
                setTypeface(null, Typeface.BOLD)
            }

            employeeLayout.addView(imageView)
            employeeLayout.addView(textView)
            rowLayout?.addView(employeeLayout)
        }

        findViewById<ImageButton>(R.id.btnZpet3).setOnClickListener {
            finish()
        }
    }
}
