package com.example.decathlon

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EidActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UkolAdapter
    private val taskList = mutableListOf<Ukol>() // Dynamický seznam úkolů

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eid)

        recyclerView = findViewById(R.id.recyclerUkoly)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btnPridatUkol = findViewById<ImageButton>(R.id.btnPridatUkol)

        // 📌 Inicializace seznamu úkolů
        taskList.addAll(
            mutableListOf(
                Ukol(1, "Dokončit report", false),
                Ukol(2, "Uklidit sklad", false),
                Ukol(3, "Inventura", true),
                Ukol(4, "Připravit podklady", false),
                Ukol(5, "Zavolat dodavateli", true)
            )
        )

        // ✅ Adapter bude používat `taskList`, aby se seznam dynamicky měnil
        adapter = UkolAdapter(taskList,
            onChecked = { position -> saveTaskStatus(position) },
            onTaskClick = { position -> showDeleteDialog(position) }
        )

        recyclerView.adapter = adapter

        // 🔙 Tlačítko zpět
        findViewById<ImageButton>(R.id.btnZpet5).setOnClickListener {
            finish()
        }

        // ➕ Přidání nového úkolu
        btnPridatUkol.setOnClickListener {
            addNewTask()
        }
    }

    // ✅ Přidá nový úkol a obnoví RecyclerView
    private fun addNewTask() {
        val newTask = Ukol(taskList.size + 1, "Nový úkol", false)
        taskList.add(newTask)
        adapter.notifyItemInserted(taskList.lastIndex) // Poslední index je vždy správný
        recyclerView.scrollToPosition(taskList.lastIndex)
    }

    // ✅ Dialog pro smazání úkolu
    private fun showDeleteDialog(position: Int) {
        if (position < 0 || position >= taskList.size) return // Ochrana proti chybám

        AlertDialog.Builder(this)
            .setTitle("Smazat úkol?")
            .setMessage("Opravdu chcete smazat tento úkol?")
            .setPositiveButton("Ano") { _, _ ->
                taskList.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, taskList.size) // Oprava indexů
            }
            .setNegativeButton("Ne", null)
            .show()
    }

    // ✅ Uloží stav checkboxu (splněno/nesplněno)
    private fun saveTaskStatus(position: Int) {
        if (position < 0 || position >= taskList.size) return // Ochrana proti chybám
        val ukol = taskList[position]
        println("Ukol '${ukol.nazev}' je nyní: ${if (ukol.splneno) "Hotovo" else "Nehotovo"}")
    }
}
