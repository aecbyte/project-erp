package com.example.erpacebyte.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.erpacebyte.R
import com.example.erpacebyte.models.TimeTableModel

class TimeTableRVAdapter(
    val context: Context,
    val timeTableList:List<TimeTableModel>

    ):RecyclerView.Adapter<TimeTableRVAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val teacherName=itemView.findViewById<TextView>(R.id.subjectTeacherName)
        val subjectName=itemView.findViewById<TextView>(R.id.subjectName)
        val roomNo=itemView.findViewById<TextView>(R.id.subjectRoomNo)
        val subjectTimeRange=itemView.findViewById<TextView>(R.id.subjectTimeRange)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.each_timetable_card,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return timeTableList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=timeTableList[position]
        holder.subjectName.text=currentItem.subjectName
        holder.teacherName.text=currentItem.teacherName
        holder.roomNo.text=currentItem.roomNo
        holder.subjectTimeRange.text=currentItem.time

    }
}