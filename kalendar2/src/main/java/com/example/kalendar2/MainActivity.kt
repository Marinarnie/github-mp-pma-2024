package com.example.kalendar2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kalendar2.databinding.ActivityMainBinding
import android.content.Intent
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private val eventList = mutableListOf<Event>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.eventsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EventAdapter(eventList)


        val btnAddEvent: Button = findViewById(R.id.btnAddEvent)
        btnAddEvent.setOnClickListener {
            // Otevření aktivity AddEventActivity
            val intent = Intent(this, AddEvent::class.java)
            startActivity(intent)
        }
    }
    // Launcher pro spuštění aktivity a příjem výsledku
    private val addEventLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val newEvent = result.data?.getSerializableExtra("new_event") as? Event
            newEvent?.let {
                eventList.add(it)
                eventAdapter.notifyItemInserted(eventList.size - 1)
                println("Událost přidána: $it")
            }
        }
    }
}