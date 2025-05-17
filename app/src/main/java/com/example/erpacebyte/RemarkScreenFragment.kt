package com.example.erpacebyte

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.erpacebyte.adapters.RemarkAdapter
import com.example.erpacebyte.mvvm.ERPUserMainViewModel

class RemarkScreenFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewBG: View
    private lateinit var noRemarkTV: TextView

    private lateinit var sharedPreferences: SharedPreferences
    private var storedUserName: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_remark_screen, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        viewBG = view.findViewById(R.id.viewBG)
        recyclerView = view.findViewById(R.id.rvRemark)
        noRemarkTV = view.findViewById(R.id.noRemarkTV)

        //ViewModel
        val viewModel = ViewModelProvider(this)[ERPUserMainViewModel::class.java]

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        storedUserName = sharedPreferences.getString("USERNAME", "") ?: ""

        if (storedUserName != ""){
            Log.d("RemarkScreen", "Fetching student details for: $storedUserName")
            viewModel.fetchRemarkList(storedUserName)
        }

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

        //handling remark details
        viewModel.remarkList.observe(viewLifecycleOwner) { remarkList->
            if(remarkList.isNullOrEmpty()){
                noRemarkTV.visibility = View.VISIBLE
                recyclerView.visibility= View.GONE
            }
            else{
                noRemarkTV.visibility = View.GONE
                recyclerView.visibility= View.VISIBLE
                recyclerView.layoutManager=LinearLayoutManager(requireContext())
                val rvAdapter= RemarkAdapter(remarkList)
                recyclerView.adapter=rvAdapter
            }
        }



        return view
    }

}