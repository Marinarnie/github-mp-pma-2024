package com.example.kalendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private val events: List<event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.eventTitle)
        val dateTextView: TextView = itemView.findViewById(R.id.eventDate)
        val indicator: View = itemView.findViewById(R.id.eventIndicator)

        fun bind(event:  event) {
            titleTextView.text = event.title
            dateTextView.text = event.date
            // Nastavte barvu indikátoru podle kategorie
            indicator.setBackgroundColor(getCategoryColor(event.category))
        }

        private fun getCategoryColor(category: String): Int {
            return when (category) {
                "osobní" -> 0xFF00FF00.toInt() // Zelená
                "pracovní" -> 0xFFFF0000.toInt() // Červená
                "zdraví" -> 0xFF0000FF.toInt() // Modrá
                else -> 0xFFFFFFFF.toInt() // Bílá (výchozí)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size
}