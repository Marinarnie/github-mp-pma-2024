package com.example.mycalendar

import com.example.mycalendar.DetailEventActivity


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventAdapter(
    private var eventList: MutableList<Event>,
    private val eventDao: EventDao
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventNameTextView: TextView = itemView.findViewById(R.id.tvEventName)
        val deleteButton: ImageButton = itemView.findViewById(R.id.btnSmazat)
        val editButton: ImageButton = itemView.findViewById(R.id.btnUpravit)
        val categoryIconImageView: ImageView = itemView.findViewById(R.id.ivCategory)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val context = itemView.context
                    val intent = Intent(context, DetailEventActivity::class.java)
                    intent.putExtra("eventName", eventList[position].name)
                    intent.putExtra("eventDate", eventList[position].date)
                    intent.putExtra("eventCategory", eventList[position].category)
                    intent.putExtra("eventId", eventList[position].id)
                    context.startActivity(intent)
                }
            }
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val event = eventList[position]
                    deleteEvent(event, position)
                }
            }

            editButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    showEditDialog(itemView, position)
                }
            }
        }
    }

    private fun deleteEvent(event: Event, position: Int) {
        // Remove from database
        CoroutineScope(Dispatchers.IO).launch {
            eventDao.deleteEvent(event)
            withContext(Dispatchers.Main) {
                eventList.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    private fun showEditDialog(view: View, position: Int) {
        val context = view.context
        val builder = androidx.appcompat.app.AlertDialog.Builder(context)
        builder.setTitle("Přejmenovat událost")

        val input = android.widget.EditText(context).apply {
            setText(eventList[position].name)
        }
        builder.setView(input)

        builder.setPositiveButton("Uložit") { dialog, _ ->
            val newName = input.text.toString()
            if (newName.isNotBlank()) {
                val event = eventList[position].copy(name = newName)
                CoroutineScope(Dispatchers.IO).launch {
                    eventDao.updateEvent(event)
                    withContext(Dispatchers.Main) {
                        eventList[position] = event
                        notifyItemChanged(position)
                    }
                }
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Zrušit") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.eventNameTextView.text = event.name
        val iconRes = when (event.category) {
            "Osobní" -> R.drawable.baseline_person_24
            "Práce" -> R.drawable.baseline_work_24
            "Rodina a kamarádi" -> R.drawable.baseline_family_restroom_24
            "Zdraví" -> R.drawable.baseline_local_hospital_24
            else -> R.drawable.baseline_person_24
        }
        holder.categoryIconImageView.setImageResource(iconRes)
    }

    override fun getItemCount(): Int = eventList.size

    fun setEvents(events: List<Event>) {
        eventList = events.toMutableList()
        notifyDataSetChanged()
    }
}


