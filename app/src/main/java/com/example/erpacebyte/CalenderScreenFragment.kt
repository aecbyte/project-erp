package com.example.erpacebyte

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.erpacebyte.adapters.EventAdapter
import com.example.erpacebyte.mvvm.ERPUserMainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class CalenderScreenFragment : Fragment() {
    private lateinit var calender:CalendarView
    private lateinit var monthTv:TextView
    private lateinit var yearTv:TextView
    private lateinit var backIVBtn: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var noEventTV: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewBG: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_calender_screen, container, false)
        calender = view.findViewById(R.id.calenderView)
        monthTv = view.findViewById(R.id.tvMonthName)
        yearTv = view.findViewById(R.id.tvYear)
        progressBar = view.findViewById(R.id.progressBar)
        viewBG = view.findViewById(R.id.viewBG)
        recyclerView = view.findViewById(R.id.eventRecyclerView)
        noEventTV = view.findViewById(R.id.noEventTV)

        //ViewModel
        val viewModel = ViewModelProvider(this)[ERPUserMainViewModel::class.java]

        //handling Loading
        viewModel.isLoadingStudentRepo.observe(viewLifecycleOwner) { loading->
            if(loading) {
                progressBar.visibility= View.VISIBLE
                viewBG.visibility= View.VISIBLE
            }
            else {
                progressBar.visibility = View.GONE
                viewBG.visibility = View.GONE
            }
        }

        viewModel.eventOfMonthList.observe(viewLifecycleOwner) { eventList ->
            if(eventList.isNullOrEmpty()){
                noEventTV.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
            else{
                noEventTV.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = EventAdapter(eventList).apply { notifyDataSetChanged() }
            }
        }

        val initialCalender = Calendar.getInstance()
        val initialMonth = initialCalender.get(Calendar.MONTH)
        val initialYear = initialCalender.get(Calendar.YEAR)

        val monthName = getMonthName(initialMonth)

        updateMonthYear(monthName, initialYear)
        viewModel.fetchMonthEventList(monthName)


        calender.setOnDateChangeListener { _, year, month, _ ->
            val selectedMonthName = getMonthName(month)
            updateMonthYear(selectedMonthName, year)
            viewModel.fetchMonthEventList(selectedMonthName)
        }

        backIVBtn = view.findViewById(R.id.ivBack)
        backIVBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_calenderScreenFragment_to_homeScreenFragment)
        }

        return view
    }

    private fun updateMonthYear(monthName: String, year: Int) {
        monthTv.text = monthName
        yearTv.text = year.toString()
    }

    private fun getMonthName(month: Int): String {
        val monthNames = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        return monthNames[month]
    }

}