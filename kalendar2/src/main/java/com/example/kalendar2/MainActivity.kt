package com.example.kalendar2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kalendar2.databinding.ActivityMainBinding
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.Toast
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
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = EventAdapter(eventList)
        eventAdapter = EventAdapter(eventList)
        recyclerView.adapter = eventAdapter


        val btnAddEvent: Button = findViewById(R.id.btnAddEvent)
        btnAddEvent.setOnClickListener {
            // Otevření aktivity AddEventActivity
            val intent = Intent(this, AddEvent::class.java)
            addEventLauncher.launch(intent)
            //startActivity(intent)
        }
    }
    // Launcher pro spuštění aktivity a příjem výsledku
    @SuppressLint("NotifyDataSetChanged")
    private val addEventLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val newEvent = result.data?.getSerializableExtra("new_event") as? Event
            newEvent?.let {
                eventList.add(it)
                //eventAdapter.notifyItemInserted(eventList.size - 1)
                //println("Událost přidána: $it")
                Toast.makeText(this, "Udalost se pridala", Toast.LENGTH_LONG).show()
                eventAdapter.notifyDataSetChanged()
            }
        }
        else{
            Toast.makeText(this, "Událost se nepodařilo vytvořit", Toast.LENGTH_LONG).show()
        }
    }



}