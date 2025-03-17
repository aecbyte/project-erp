package com.example.erpacebyte

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController

class LeaveRequestScreenFragment : Fragment() {
    private lateinit var backIVBtn: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_leave_request_screen, container, false)
        backIVBtn = view.findViewById(R.id.ivBack)
        backIVBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_leaveRequestScreenFragment_to_homeScreenFragment)
        }

        return view
    }

}