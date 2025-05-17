package com.example.erpacebyte

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.erpacebyte.adapters.LeaveAdapter
import com.example.erpacebyte.mvvm.ERPUserMainViewModel
import com.google.android.material.snackbar.Snackbar


class LeaveScreenFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var viewBG: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var noLeaveTV: TextView
    private lateinit var applyLeaveBtn: Button
    private lateinit var sharedPreferences: SharedPreferences
    private var storedUserName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_leave_screen, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        viewBG = view.findViewById(R.id.viewBG)
        recyclerView = view.findViewById(R.id.recyclerViewLeave)
        noLeaveTV = view.findViewById(R.id.noLeaveTV)
        applyLeaveBtn = view.findViewById(R.id.btnApplyLeave)

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        storedUserName = sharedPreferences.getString("USERNAME", "") ?: ""

        //ViewModel
        val viewModel = ViewModelProvider(this)[ERPUserMainViewModel::class.java]
        if (storedUserName!="") viewModel.fetchUserLeave(storedUserName)

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

        //handling btnClick
        applyLeaveBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_leaveScreenFragment_to_leaveRequestScreenFragment)
        }

        //handling leave list
        viewModel.leaveList.observe(viewLifecycleOwner) { list->
            if(list.isNullOrEmpty()){
                Snackbar.make(view,"No Applied Leaves", Snackbar.LENGTH_SHORT).show()
                recyclerView.visibility= View.GONE
                noLeaveTV.visibility= View.VISIBLE
            }
            else{
                val adapter = LeaveAdapter(list)
                recyclerView.visibility= View.VISIBLE
                noLeaveTV.visibility= View.GONE
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
            }
        }

        return view
    }
}