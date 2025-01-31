package com.example.mycalendar

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import androidx.room.Room
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.Locale.Category

class MainActivity : AppCompatActivity() {

    private lateinit var vybranyDatum: TextView
    private lateinit var calendarView: CalendarView
    private lateinit var btnPridat: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private lateinit var eventDao: EventDao
    private lateinit var database: AppDatabase

    private val eventList = mutableListOf<Event>() // Seznam událostí

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        setLocale(Locale("cs", "CZ"))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = AppDatabase.getDatabase(this)
        eventDao = database.eventDao()

        vybranyDatum = findViewById(R.id.etVybranyDatum)
        calendarView = findViewById(R.id.etCalendarView)
        btnPridat = findViewById(R.id.btnPridat)
        recyclerView = findViewById(R.id.etSeznam)

        recyclerView.layoutManager = LinearLayoutManager(this)
        eventAdapter = EventAdapter(mutableListOf(), eventDao)
        recyclerView.adapter = eventAdapter

        setTodayDate()

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val formattedDate = String.format(Locale("cs"), "%02d.%02d.%d", dayOfMonth, month + 1, year)
            vybranyDatum.text = formattedDate
            filterEventsByDate(formattedDate)
        }

        btnPridat.setOnClickListener {
            openAddEventDialog()
        }
    }

    override fun onResume() {
        super.onResume()
        val selectedDate = vybranyDatum.text.toString()
        filterEventsByDate(selectedDate)
    }

    private fun filterEventsByDate(date: String) {
        lifecycleScope.launch {
            val filteredEvents = eventDao.getAllEvents().filter { it.date == date }
            eventAdapter.setEvents(filteredEvents)
        }
    }

    private fun setLocale(locale: Locale) {
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun setTodayDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val formattedDate = String.format(Locale("cs"), "%02d.%02d.%d", dayOfMonth, month + 1, year)
        vybranyDatum.text = formattedDate
    }

    private fun openAddEventDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_event, null)

        val eventNameEditText = dialogView.findViewById<EditText>(R.id.etEventName)
        val dateTextView = dialogView.findViewById<TextView>(R.id.etEventDate)

        dateTextView.text = vybranyDatum.text

        builder.setView(dialogView)
            .setPositiveButton("Uložit") { dialog, _ ->
                val eventName = eventNameEditText.text.toString()
                val eventDate = dateTextView.text.toString()
                val categorySpinner = dialogView.findViewById<Spinner>(R.id.etSpinner)
                val selectedCategory = categorySpinner.selectedItem.toString()

                val newEvent = Event(
                    name = eventName,
                    date = eventDate,
                    category = selectedCategory,
                    imageUri = null
                )
                lifecycleScope.launch {
                    eventDao.insertEvent(newEvent)
                    filterEventsByDate(eventDate) // Update events after adding
                }
                dialog.dismiss()
            }
            .setNegativeButton("Zrušit") { dialog, _ ->
                dialog.dismiss()
            }

        builder.create().show()
    }
}

