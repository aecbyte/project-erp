package com.example.erpacebyte

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class CalenderScreenFragment : Fragment() {
    private lateinit var calender:CalendarView
    private lateinit var monthTv:TextView
    private lateinit var yearTv:TextView
    private lateinit var backIVBtn: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmento
        val view= inflater.inflate(R.layout.fragment_calender_screen, container, false)
        calender = view.findViewById(R.id.calenderView)
        monthTv = view.findViewById(R.id.tvMonthName)
        yearTv = view.findViewById(R.id.tvYear)

        val initialCalender = Calendar.getInstance()
        updateMonthYear(initialCalender.get(Calendar.MONTH),initialCalender.get(Calendar.YEAR))

        calender.setOnDateChangeListener { _, year, month, _ ->
            updateMonthYear(month, year)
        }

        backIVBtn = view.findViewById(R.id.ivBack)
        backIVBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_calenderScreenFragment_to_homeScreenFragment)
        }

        return view
    }

    private fun updateMonthYear(month: Int, year: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)

        val monthName = SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time)
        monthTv.text = monthName
        yearTv.text = year.toString()
    }

}