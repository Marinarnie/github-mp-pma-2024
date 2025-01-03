package com.example.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private val eventList: MutableList<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventNameTextView: TextView = itemView.findViewById(R.id.tvEventName)
        val eventDateTextView: TextView = itemView.findViewById(R.id.tvEventDate)
        val deleteButton: ImageButton = itemView.findViewById(R.id.btnSmazat)

        init {
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Smazání události
                    eventList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }
    // Vytvoření ViewHolderu pro zobrazení položek seznamu
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    // Nastavení dat pro jednotlivé položky
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.eventNameTextView.text = event.name
        holder.eventDateTextView.text = event.date
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    // Přidání nové události do seznamu
    fun addEvent(event: Event) {
        eventList.add(event)
        notifyItemInserted(eventList.size - 1)  // Oznámení o vložení nové položky
    }
}
