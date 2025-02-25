package com.example.decathlon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class RokAdapter(
    private val roky: MutableList<Rok>,
    private val onClick: (Rok) -> Unit
) : RecyclerView.Adapter<RokAdapter.RokViewHolder>() {

    class RokViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnRok: Button = view.findViewById(R.id.btnRokItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RokViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rok, parent, false)
        return RokViewHolder(view)
    }

    override fun onBindViewHolder(holder: RokViewHolder, position: Int) {
        val rok = roky[position]
        holder.btnRok.text = rok.nazev
        holder.btnRok.setOnClickListener { onClick(rok) }
    }

    override fun getItemCount(): Int = roky.size
}
