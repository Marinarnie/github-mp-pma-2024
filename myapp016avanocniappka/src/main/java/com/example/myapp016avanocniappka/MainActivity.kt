package com.example.myapp016avanocniappka

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.widget.TextView
import java.util.*
import android.animation.ObjectAnimator
import android.content.Intent
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.core.content.res.ResourcesCompat
import android.text.SpannableString
import android.text.style.StyleSpan
import android.graphics.Typeface
import android.widget.Button
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var countdownText: TextView
    private val targetDate = Calendar.getInstance().apply {
        set(2024, Calendar.DECEMBER, 24, 0, 0, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnHrat: Button = findViewById(R.id.btnHrat)

        // Nastavení listeneru pro tlačítko
        btnHrat.setOnClickListener {
            // Vytvoření intentu pro přechod na DruhaAktivita
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent) // Spuštění nové aktivity
        }

        countdownText = findViewById(R.id.countdownText)

        // Spustit odpočítávání
        startCountdown()

        // Spustit efekt padajícího sněhu
        startSnowfall()
    }

    private fun startCountdown() {
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                val now = System.currentTimeMillis()
                val timeDifference = targetDate - now

                val days = timeDifference / (1000 * 60 * 60 * 24)
                val hours = (timeDifference / (1000 * 60 * 60)) % 24
                val minutes = (timeDifference / (1000 * 60)) % 60
                val seconds = (timeDifference / 1000) % 60

                countdownText.text = "$days dní, $hours hodin, $minutes minut, $seconds sekund"

                if (timeDifference > 0) {
                    handler.postDelayed(this, 1000)
                }
            }
        }
        handler.post(runnable)
    }

    private fun startSnowfall() {
        val snowView = findViewById<ViewGroup>(R.id.snowView)

        // Generování sněhových vloček v pravidelných intervalech
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    createSnowflake(snowView)
                }
            }
        }, 0, 200) // Generovat každé 300 ms
    }

    private fun createSnowflake(parent: ViewGroup) {
        val snowflake = ImageView(this)
        snowflake.setImageResource(R.drawable.pngegg_snowflake) // Nahraďte "pngegg_snowflakes" názvem vašeho obrázku sněhové vločky
        val size = Random.nextInt(30, 100) // Náhodná velikost sněhové vločky
        snowflake.layoutParams = ViewGroup.LayoutParams(size, size)

        // Nastavení počáteční pozice
        snowflake.x = Random.nextFloat() * parent.width
        snowflake.y = -size.toFloat()


        parent.addView(snowflake) // Přidání vločky do rodičovského View

        // Animace padající sněhové vločky
        ObjectAnimator.ofFloat(snowflake, "y", parent.height.toFloat()).apply {
            duration = Random.nextLong(3000, 5000) // Doba trvání animace
            start()
            doOnEnd { parent.removeView(snowflake) } // Odstranit vločku po dokončení animace
        }
    }
        }
