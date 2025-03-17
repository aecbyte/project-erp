package com.example.erpacebyte

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.navigation.findNavController

class ResultScreenFragment : Fragment() {
    private lateinit var backIVBtn:ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_result_screen, container, false)
        val sessionList = resources.getStringArray(R.array.sessionsResults)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, sessionList)

        val autocompleteTV = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)

        autocompleteTV.setAdapter(arrayAdapter)

        backIVBtn = view.findViewById(R.id.ivBack)
        backIVBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_resultScreenFragment_to_homeScreenFragment)
        }

        return view
    }

}