package com.example.mycalendar2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.graphics.Color
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.decorators.EventDecorator
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var calendarView: MaterialCalendarView
    private val events = mutableListOf<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendarView)
        val addEventButton: Button = findViewById(R.id.addEventButton)

        // Přidat událost
        addEventButton.setOnClickListener {
            showAddEventDialog()
        }

        // Kliknutí na den v kalendáři
        calendarView.setOnDateChangedListener { _, date, _ ->
            val selectedEvents = events.filter { it.date == date }
            if (selectedEvents.isNotEmpty()) {
                showEventsDialog(selectedEvents)
            } else {
                Toast.makeText(this, "Žádné události pro tento den.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Dialog pro přidání události
    private fun showAddEventDialog() {
        val categories = mapOf(
            "Práce" to Color.BLUE,
            "Osobní" to Color.GREEN,
            "Rodina" to Color.RED
        )
        val categoriesKeys = categories.keys.toTypedArray()

        val builder = AlertDialog.Builder(this)
        val layout = layoutInflater.inflate(R.layout.dialog_add_event, null)
        builder.setView(layout)

        val dialog = builder.create()
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Přidat") { _, _ ->
            val selectedDate = calendarView.selectedDate
            val category = categoriesKeys.random()
            val color = categories[category] ?: Color.GRAY
            val event = Event(selectedDate!!, category, color)
            events.add(event)
            updateCalendar()
        }
        dialog.show()
    }

}


