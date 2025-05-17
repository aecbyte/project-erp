package com.example.erpacebyte.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.erpacebyte.R
import com.example.erpacebyte.models.RemarkModel

class RemarkAdapter(private val remarkList: List<RemarkModel>) :
    RecyclerView.Adapter<RemarkAdapter.RemarkViewHolder>() {

    inner class RemarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val senderNameTV: TextView = itemView.findViewById(R.id.remarkSenderNameTV)
        val sentDateTV: TextView = itemView.findViewById(R.id.remarkSentDateTV)
        val descriptionTV: TextView = itemView.findViewById(R.id.remarkDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemarkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.each_remark_screen_item, parent, false) // Replace with actual layout file name
        return RemarkViewHolder(view)
    }

    override fun onBindViewHolder(holder: RemarkViewHolder, position: Int) {
        val remark = remarkList[position]

        holder.senderNameTV.text = remark.senderName
        holder.sentDateTV.text = "Date- ${remark.remarkDate}"
        holder.descriptionTV.text = remark.remarkDescription
    }

    override fun getItemCount(): Int = remarkList.size
}
