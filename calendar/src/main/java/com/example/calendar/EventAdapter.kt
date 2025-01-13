package com.example.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private val eventList: MutableList<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventNameTextView: TextView = itemView.findViewById(R.id.tvEventName)
        val deleteButton: ImageButton = itemView.findViewById(R.id.btnSmazat)
        val categoryIconImageView: ImageView = itemView.findViewById(R.id.ivCategory)

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
        // Nastavení ikony podle kategorie
        val iconRes = when (event.category) {
            "Osobní" -> R.drawable.baseline_person_24
            "Pracovní" -> R.drawable.baseline_work_24
            "Rodina a kamarádi" -> R.drawable.baseline_family_restroom_24
            "Zdravotní" -> R.drawable.baseline_local_hospital_24
                else -> R.drawable.baseline_person_24
        }
        holder.categoryIconImageView.setImageResource(iconRes)

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
