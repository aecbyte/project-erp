package com.example.erpacebyte.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.erpacebyte.R
import com.example.erpacebyte.models.EventModel

class EventAdapter(private val eventList: List<EventModel>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_calender_item, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.eventNameTV.text = event.eventName
        holder.eventTypeTV.text = event.eventType
        holder.eventDateTV.text = event.eventFullDate

        if(holder.eventTypeTV.text == "Holiday"){
            holder.eventTypeTV.setTextColor(holder.itemView.context.resources.getColor(R.color.eventRed))
            holder.eventCardBorder.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.eventRed))

        }
    }

    override fun getItemCount(): Int = eventList.size

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventNameTV: TextView = itemView.findViewById(R.id.eventNameTV)
        val eventTypeTV: TextView = itemView.findViewById(R.id.eventTypeTV)
        val eventDateTV: TextView = itemView.findViewById(R.id.eventDateTV)
        val eventCardBorder: View = itemView.findViewById(R.id.viewEventBorder)
    }
}