package com.example.kalendar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private lateinit var events: List<Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.eventsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        events = loadEvents()

        eventAdapter = EventAdapter(events)
        recyclerView.adapter = eventAdapter

    }

    private fun loadEvents() {
        var oneEvent: Event
        oneEvent.date = ""
        oneEvent.title = ""
        oneEvent.category = ""

        events.add(oneEvent)

        var oneEvent: Event
        oneEvent.date = ""
        oneEvent.title = ""
        oneEvent.category = ""
    }
}