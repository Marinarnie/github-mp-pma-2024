package com.example.decathlon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

// Model třída pro úkol
data class Ukol(
    val id: Int,
    var nazev: String,
    var splneno: Boolean
)

class UkolAdapter(
    private val ukoly: MutableList<Ukol>,
    private val onChecked: (Int) -> Unit,  // Callback pro změnu checkboxu
    private val onTaskClick: (Int) -> Unit // Callback pro kliknutí na úkol (mazání)
) : RecyclerView.Adapter<UkolAdapter.UkolViewHolder>() {

    class UkolViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val editText: EditText = view.findViewById(R.id.etNazevUkolu)
        val checkBox: CheckBox = view.findViewById(R.id.checkBoxUkol)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UkolViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ukol, parent, false)
        return UkolViewHolder(view)
    }

    override fun onBindViewHolder(holder: UkolViewHolder, position: Int) {
        val ukol = ukoly[position]
        holder.editText.setText(ukol.nazev)
        holder.checkBox.isChecked = ukol.splneno

        // Kliknutí na checkbox -> změna stavu
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            ukol.splneno = isChecked
            onChecked(position) // Volání callbacku
        }

        // Kliknutí na EditText -> zobrazí dialog pro smazání
        holder.editText.setOnClickListener {
            onTaskClick(position)
        }

        // Uloží změnu textu při ztrátě focusu
        holder.editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                ukoly[position].nazev = holder.editText.text.toString()
            }
        }
    }

    override fun getItemCount() = ukoly.size
}
