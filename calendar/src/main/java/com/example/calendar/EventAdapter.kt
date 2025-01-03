package com.example.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private val eventList: MutableList<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    // Vytvoření ViewHolderu pro zobrazení položek seznamu
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    // Nastavení dat pro jednotlivé položky
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.nameTextView.text = event.name
        holder.dateTextView.text = event.date
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    // Přidání nové události do seznamu
    fun addEvent(event: Event) {
        eventList.add(event)
        notifyItemInserted(eventList.size - 1)  // Oznámení o vložení nové položky
    }

    // ViewHolder pro položky v RecyclerView
    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tvEventName)
        val dateTextView: TextView = itemView.findViewById(R.id.tvEventDate)
    }
}
