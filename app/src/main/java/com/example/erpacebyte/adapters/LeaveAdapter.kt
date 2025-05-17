package com.example.erpacebyte.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.erpacebyte.R
import com.example.erpacebyte.models.LeaveModel

class LeaveAdapter(private val leaveList: List<LeaveModel>) :
    RecyclerView.Adapter<LeaveAdapter.LeaveViewHolder>() {

    class LeaveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFromDate: TextView = itemView.findViewById(R.id.tvFromDate)
        val tvTillDate: TextView = itemView.findViewById(R.id.tvTillDate)
        val tvLeaveReason: TextView = itemView.findViewById(R.id.tvLeaveReason)
        val tvLeaveStatus: TextView = itemView.findViewById(R.id.tvLeaveStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaveViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leave, parent, false) // replace with actual layout file
        return LeaveViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeaveViewHolder, position: Int) {
        val leave = leaveList[position]

        holder.tvFromDate.text = "From - ${leave.leaveFromDate}"
        holder.tvTillDate.text = "Till - ${leave.leaveToDate}"
        holder.tvLeaveReason.text = leave.leaveType
        holder.tvLeaveStatus.text = leave.leaveStatus

        // Set background drawable based on status
        val context = holder.itemView.context
        val statusBg = when (leave.leaveStatus.lowercase()) {
            "accepted" -> R.drawable.status_accepted
            "pending" -> R.drawable.status_rejected
            "rejected" -> R.drawable.status_rejected
            else -> R.drawable.status_rejected
        }
        holder.tvLeaveStatus.setBackgroundResource(statusBg)
    }

    override fun getItemCount(): Int = leaveList.size
}