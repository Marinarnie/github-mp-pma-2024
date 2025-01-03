package com.example.calendar

import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var vybranyDatum: TextView
    lateinit var calendarView: CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        setLocale(Locale("cs", "CZ"))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vybranyDatum = findViewById(R.id.etVybranyDatum)
        calendarView = findViewById(R.id.etCalendarView)

        //listener pro změnu data v kalendáři
        calendarView.setOnDateChangeListener(
            //format data do češtiny
                CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
                    val formattedDate = String.format(Locale("cs"), "%02d.%02d.%d", dayOfMonth, month + 1, year)
                    vybranyDatum.text = formattedDate
                })
    }
    private fun setLocale(locale: Locale) {
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        // Aktualizace konfigurace aplikace
        createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
