package com.example.kalendar2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.DatePickerDialog
import android.widget.Button
import android.widget.EditText
import java.util.*

data class Event(
    val title: String,
    val date:String,
    val category: String
)

class AddEvent : AppCompatActivity() {

    private lateinit var datumEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_event)

        val btnZpet: Button = findViewById(R.id.btnZpet)

        datumEditText = findViewById(R.id.editDatum)

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
            datumEditText.setText("$selectedDay/$formattedMonth/$selectedYear")
        }, year, month, day)

        datePickerDialog.show()
    }

}