package com.example.kalendar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.eventsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val events = listOf(
            event("Narozeniny Jana", "2024-11-20", "osobní"),
            event("Schůzka s klientem", "2024-11-21", "pracovní"),
            event("Zubař", "2024-11-22", "zdraví")
        )

        eventAdapter = EventAdapter(events)
        recyclerView.adapter = eventAdapter

    }
}