package com.example.kalendar2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import java.util.*
import android.widget.AutoCompleteTextView
import android.widget.Toast
import java.io.Serializable

data class Event(
    val title: String,
    val date:String,
    val category: String
) : Serializable

class AddEvent : AppCompatActivity() {

    private lateinit var datumEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_event)

        val btnZpet: Button = findViewById(R.id.btnZpet)
        val title: EditText = findViewById(R.id.etUdalost)
        val category: AutoCompleteTextView = findViewById(R.id.etCategory)
        val btnAdd: Button = findViewById(R.id.btnAdd)
        datumEditText = findViewById(R.id.etDatum)


        // Seznam kategorií
        val categories = listOf(
            "Důležitá data",
            "Pracovní",
            "Zdravotní",
            "Vzdělání",
            "Volný čas",
            "Osobní důležité"
        )
        // Nastavení adaptéru pro AutoCompleteTextView
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            categories
        )
        category.setAdapter(adapter)

        // Akce pro tlačítko Uložit
        btnAdd.setOnClickListener {
            val eventTitle = title.text.toString() // Použij deklarovanou proměnnou "title"
            val eventDate = datumEditText.text.toString() // Použij deklarovanou proměnnou "datumEditText"
            val eventCategory = category.text.toString() // Použij deklarovanou proměnnou "category"

            // Zde je logika pro uložení události (např. do databáze nebo předání zpět)
            if (eventTitle.isNotEmpty() && eventDate.isNotEmpty() && eventCategory.isNotEmpty()) {
                val newEvent = Event(eventTitle, eventDate, eventCategory) // Vytvoření události uvnitř onClickListener
                val resultIntent = Intent()
                resultIntent.putExtra("new_event", newEvent)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Vyplňte všechny údaje", Toast.LENGTH_SHORT).show()
            }
        }

        category.setOnClickListener {
            category.showDropDown()
        }

        datumEditText.setOnClickListener {
            showDatePickerDialog()
        }
        btnZpet.setOnClickListener {
            // Zpět na MainActivity
            finish() // Zavře aktuální aktivitu a vrátí se na předchozí
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            // Formátování měsíce (přidání 1, protože měsíc začíná od 0)
            val formattedMonth = selectedMonth + 1
            // Nastavení vybraného data do EditTextu
            datumEditText.setText("$selectedDay-$formattedMonth-$selectedYear")
        }, year, month, day)

        datePickerDialog.show()
    }

}