package com.example.calendar

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var vybranyDatum: TextView
    lateinit var calendarView: CalendarView
    lateinit var btnPridat: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        setLocale(Locale("cs", "CZ"))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vybranyDatum = findViewById(R.id.etVybranyDatum)
        calendarView = findViewById(R.id.etCalendarView)
        btnPridat = findViewById(R.id.btnPridat)

        //listener pro změnu data v kalendáři
        calendarView.setOnDateChangeListener(
            //format data do češtiny
                CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
                    val formattedDate = String.format(Locale("cs"), "%02d.%02d.%d", dayOfMonth, month + 1, year)
                    vybranyDatum.text = formattedDate
                })
        btnPridat.setOnClickListener {
            openAddEventDialog()
        }
    }
    private fun setLocale(locale: Locale) {
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        // Aktualizace konfigurace aplikace
        createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
    private fun openAddEventDialog() {
        // Vytvoření dialogu pro přidání události
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_event, null)

        // Inicializace komponent dialogu
        val eventNameEditText = dialogView.findViewById<EditText>(R.id.etEventName)
        val dateTextView = dialogView.findViewById<TextView>(R.id.etEventDate)

        // Zobrazení aktuálního data (datum z CalendarView)
        dateTextView.text = vybranyDatum.text

        // DatePicker dialog pro výběr data
        dateTextView.setOnClickListener {
            showDatePickerDialog(dateTextView)
        }

        builder.setView(dialogView)
            .setPositiveButton("Uložit") { dialog, _ ->
                val eventName = eventNameEditText.text.toString()
                val eventDate = dateTextView.text.toString()
                // Tady můžeš uložit událost nebo ji zobrazit v seznamu
                Toast.makeText(this, "Událost: $eventName, Datum: $eventDate", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Zrušit") { dialog, _ ->
                dialog.dismiss()
            }

        builder.create().show()
    }

    private fun showDatePickerDialog(dateTextView: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Vytvoření DatePicker dialogu
        val datePickerDialog = DatePickerDialog(
            this, { _, selectedYear, selectedMonth, selectedDay ->
                // Formátování a nastavení vybraného data
                val formattedDate = String.format(Locale("cs"), "%02d.%02d.%d", selectedDay, selectedMonth + 1, selectedYear)
                dateTextView.text = formattedDate
            }, year, month, day
        )
        datePickerDialog.show()
    }
}
