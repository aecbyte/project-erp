package com.example.erpacebyte.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.erpacebyte.R
import com.example.erpacebyte.models.AttendanceModel

class AttendanceRVAdapter(private val attendanceList: List<AttendanceModel>) :
    RecyclerView.Adapter<AttendanceRVAdapter.AttendanceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.each_attendence_status_card, parent, false)
        return AttendanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        val item = attendanceList[position]
        holder.tvDate.text = item.fullDate
        holder.tvStatus.text = item.attendanceStatus

        if(holder.tvStatus.text == "Absent"){
            holder.tvStatus.setBackgroundResource(R.drawable.attendence_status_bg_btn_absent)
        }
        else if(holder.tvStatus.text == "Leave"){
            holder.tvStatus.setBackgroundResource(R.drawable.attendence_status_bg_btn_leave)
        }
    }

    override fun getItemCount(): Int = attendanceList.size

    class AttendanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
    }
}