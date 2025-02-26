package com.example.decathlon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RokActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RokAdapter
    private val rokyList = mutableListOf<Int>() // Seznam roků

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rok)

        val numberPicker = findViewById<NumberPicker>(R.id.numberPickerRok)
        val btnPridatRok = findViewById<Button>(R.id.btnPridatRok)
        recyclerView = findViewById(R.id.recyclerRoky)

        // Nastavení NumberPickeru
        numberPicker.minValue = 1995
        numberPicker.maxValue = 2025
        numberPicker.value = 2024

        // Nastavení RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RokAdapter(rokyList) { vybranyRok ->
            otevritMesiceActivity(vybranyRok)
        }
        recyclerView.adapter = adapter

        // Přidání roku
        btnPridatRok.setOnClickListener {
            val vybranyRok = numberPicker.value
            if (!rokyList.contains(vybranyRok)) {
                rokyList.add(vybranyRok)
                adapter.notifyItemInserted(rokyList.size - 1)
            }
        }
    }

    // Otevře aktivitu s měsíci
    private fun otevritMesiceActivity(rok: Int) {
        val intent = Intent(this, MesicActivity::class.java)
        intent.putExtra("VYBRANY_ROK", rok)
        startActivity(intent)
    }
}
