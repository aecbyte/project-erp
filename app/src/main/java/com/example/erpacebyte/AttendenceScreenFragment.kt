package com.example.erpacebyte

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.erpacebyte.adapters.AttendanceRVAdapter
import com.example.erpacebyte.mvvm.ERPUserMainViewModel
import com.google.android.material.textfield.TextInputLayout

class AttendenceScreenFragment : Fragment() {
    private lateinit var backIVBtn:ImageView
    private lateinit var inputChooseMonth: TextInputLayout
    private lateinit var monthAutoCompTV: AutoCompleteTextView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewBG: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var noAttendanceTV: TextView

    private lateinit var sharedPreferences: SharedPreferences
    private var storedUserName: String = ""
    private val months = arrayOf(
        "January 2025", "February 2025", "March 2025", "April 2025", "May 2025", "June 2025",
        "July 2025", "August 2025", "September 2025", "October 2025", "November 2025", "December 2025"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_attendence_screen, container, false)
        backIVBtn = view.findViewById(R.id.ivBack)
        progressBar = view.findViewById(R.id.progressBar)
        viewBG = view.findViewById(R.id.viewBG)
        inputChooseMonth = view.findViewById(R.id.inputChooseMonth)
        monthAutoCompTV = view.findViewById(R.id.autoCompleteTextViewMonth)
        recyclerView = view.findViewById(R.id.rvAttendanceStatus)
        noAttendanceTV = view.findViewById(R.id.noAttendanceTV)

        //ViewModel
        val viewModel = ViewModelProvider(this)[ERPUserMainViewModel::class.java]

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        storedUserName = sharedPreferences.getString("USERNAME", "") ?: ""

        //Handling DropDown
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, months)
        monthAutoCompTV.setAdapter(adapter)

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

        // Handling Student Attendance
        viewModel.attendanceOfMonthList.observe(viewLifecycleOwner){ attendanceList ->
            if(attendanceList.isNullOrEmpty()){
                noAttendanceTV.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
            else{
                noAttendanceTV.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = AttendanceRVAdapter(attendanceList).apply { notifyDataSetChanged() }
            }
        }

        // Handle selection
        monthAutoCompTV.setOnItemClickListener { _, _, position, _ ->
            val selectedMonth = months[position]
            monthAutoCompTV.setText(selectedMonth, false) // Set the selected value
            val selectedMonthString = selectedMonth.replace(" ","")
            viewModel.fetchMonthAttendance(storedUserName,selectedMonthString)
        }


        backIVBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_attendenceScreenFragment_to_homeScreenFragment)
        }

        return view
    }


}