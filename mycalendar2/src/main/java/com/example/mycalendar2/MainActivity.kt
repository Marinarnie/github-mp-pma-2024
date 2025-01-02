package com.example.mycalendar2

import android.app.AlertDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.CalendarView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.applandeo.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private var events: MutableMap<String, String> = mutableMapOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calendarView = findViewById(R.id.calendar)

        val calendar: ArrayList<CalendarDay> = ArrayList()
        val calendar = Calendar.getInstance()

        calendar.set(2024,7,20)
        val calendarDay = CalendarDay(calendar)
        calendarDay.labelColor = R.color.purple
        calendarDay.imageResource = R.drawable
    }
}
