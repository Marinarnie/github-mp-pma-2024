package com.example.decathlon

import android.os.Bundle
import android.widget.EditText
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

        // ✅ Adapter bez předem daných úkolů
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
            showAddTaskDialog()
        }
    }

    // ✅ Zobrazí dialog pro zadání názvu úkolu
    private fun showAddTaskDialog() {
        val inputField = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("Přidat nový úkol")
            .setMessage("Zadejte název úkolu:")
            .setView(inputField)
            .setPositiveButton("Přidat") { _, _ ->
                val taskName = inputField.text.toString().trim()
                if (taskName.isNotEmpty()) {
                    addNewTask(taskName)
                }
            }
            .setNegativeButton("Zrušit", null)
            .show()
    }

    // ✅ Přidá nový úkol do seznamu a aktualizuje RecyclerView
    private fun addNewTask(taskName: String) {
        val newTask = Ukol(taskList.size + 1, taskName, false)
        taskList.add(newTask)
        adapter.notifyItemInserted(taskList.lastIndex)
        recyclerView.scrollToPosition(taskList.lastIndex)
    }

    // ✅ Dialog pro smazání úkolu
    private fun showDeleteDialog(position: Int) {
        if (position < 0 || position >= taskList.size) return

        AlertDialog.Builder(this)
            .setTitle("Smazat úkol?")
            .setMessage("Opravdu chcete smazat tento úkol?")
            .setPositiveButton("Ano") { _, _ ->
                taskList.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, taskList.size)
            }
            .setNegativeButton("Ne", null)
            .show()
    }

    // ✅ Uloží stav checkboxu (splněno/nesplněno)
    private fun saveTaskStatus(position: Int) {
        if (position < 0 || position >= taskList.size) return
        val ukol = taskList[position]
        println("Úkol '${ukol.nazev}' je nyní: ${if (ukol.splneno) "Hotovo" else "Nehotovo"}")
    }
}
