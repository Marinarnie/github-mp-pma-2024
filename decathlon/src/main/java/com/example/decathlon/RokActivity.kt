package com.example.decathlon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RokActivity : AppCompatActivity() {
    private val seznamRoku = mutableListOf<Rok>() // Seznam roků
    private lateinit var adapter: RokAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rok)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerRoky)
        val btnPridatRok = findViewById<Button>(R.id.btnPridatRok)

        // Nastavení RecyclerView
        adapter = RokAdapter(seznamRoku) { rok ->
            val intent = Intent(this, MesicActivity::class.java)
            intent.putExtra("VYBRANY_ROK", rok.nazev)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Přidání nového roku
        btnPridatRok.setOnClickListener {
            pridatNovyRok()
        }
    }

    // Funkce pro přidání roku
    private fun pridatNovyRok() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Přidat nový rok")

        val input = android.widget.EditText(this)
        input.hint = "Např. 2023"
        builder.setView(input)

        builder.setPositiveButton("Přidat") { _, _ ->
            val novyRok = input.text.toString()
            if (novyRok.isNotEmpty()) {
                seznamRoku.add(Rok(novyRok))
                adapter.notifyItemInserted(seznamRoku.size - 1)
            }
        }
        builder.setNegativeButton("Zrušit", null)
        builder.show()
    }
}
