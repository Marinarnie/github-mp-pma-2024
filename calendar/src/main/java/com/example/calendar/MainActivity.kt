package com.example.calendar

import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var dateTV: TextView
    lateinit var calendarView: CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Nastavení češtiny jako výchozího jazyka
        val locale = Locale("cs", "CZ")
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Inicializace TextView a CalendarView
        dateTV = findViewById(R.id.idTVDate)
        calendarView = findViewById(R.id.calendarView)

        // Nastavení prvního dne týdne na pondělí
        val calendar = Calendar.getInstance(locale)
        calendar.firstDayOfWeek = Calendar.MONDAY

        // Posluchač pro změnu datumu
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = "$dayOfMonth-${month + 1}-$year"
            dateTV.text = date
        }
    }
}
