package com.example.erpacebyte

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.erpacebyte.models.LeaveModel
import com.example.erpacebyte.mvvm.ERPUserMainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LeaveRequestScreenFragment : Fragment() {
    private lateinit var backIVBtn: ImageView
    private lateinit var leaveDescEditText: TextInputEditText
    private lateinit var leaveTypeInpLayout: TextInputLayout
    private lateinit var leaveTypeAutoCompleteTV: AutoCompleteTextView
    private lateinit var leaveFromDateInpEditText: TextInputEditText
    private lateinit var leaveToDateInpEditText: TextInputEditText
    private lateinit var leaveMedicalUrlUpload : ImageButton
    private lateinit var leaveMedFileNameTV: TextView
    private lateinit var leaveApplyBtn: Button

    private lateinit var progressBar: ProgressBar
    private lateinit var viewBG: View

    private lateinit var sharedPreferences: SharedPreferences
    private var storedUserName: String = ""
    private var storedActualName: String = ""
    private var storedUserType: String = ""
    private val leaveTypes = arrayOf(
        "Medical Issue" , "Family Issue" , "Events / Functions" , "Vacation"
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_leave_request_screen, container, false)
        backIVBtn = view.findViewById(R.id.ivBack)
        leaveDescEditText = view.findViewById(R.id.leaveDescInpEditText)
        leaveTypeInpLayout = view.findViewById(R.id.leaveTypeInpLayout)
        leaveTypeAutoCompleteTV =view.findViewById(R.id.leaveTypeAutoCompleteTV)
        leaveFromDateInpEditText = view.findViewById(R.id.leaveFromDateInpEditText)
        leaveToDateInpEditText = view.findViewById(R.id.leaveToDateInpEditText)
        leaveMedicalUrlUpload =view.findViewById(R.id.leaveMedicalFileIB)
        leaveMedFileNameTV =view.findViewById(R.id.leaveMedicalFileNameTV)
        leaveApplyBtn = view.findViewById(R.id.leaveApplyBtn)

        progressBar = view.findViewById(R.id.progressBar)
        viewBG = view.findViewById(R.id.viewBG)

        //ViewModel
        val viewModel = ViewModelProvider(this)[ERPUserMainViewModel::class.java]

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        storedUserName = sharedPreferences.getString("USERNAME", "") ?: ""
        storedActualName = sharedPreferences.getString("USERACTUALNAME", "") ?: ""
        storedUserType = sharedPreferences.getString("USERTYPE", "") ?: ""

        if (storedUserName != ""){
            Log.d("HomeScreen", "Fetching student details for: $storedUserName")
            viewModel.fetchStudentDetail(storedUserName)
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

        //handling leaveType dropdown
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, leaveTypes)
        leaveTypeAutoCompleteTV.setAdapter(adapter)

        //handling medical file btn
        leaveMedicalUrlUpload.setOnClickListener {
            Snackbar.make(view,"Feature not available", Snackbar.LENGTH_SHORT).show()
        }

        //handling btnClick
        leaveApplyBtn.setOnClickListener {
            val leaveDesc = leaveDescEditText.text.toString()
            val leaveType = leaveTypeAutoCompleteTV.text.toString()
            val leaveFromDate = leaveFromDateInpEditText.text.toString()
            val leaveToDate = leaveToDateInpEditText.text.toString()

            if (leaveDesc.isBlank() || leaveType.isBlank() || leaveFromDate.isBlank() || leaveToDate.isBlank()) {
                Snackbar.make(view, "Please fill all the details", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (leaveDesc.length < 10) {
                Snackbar.make(view, "Leave Description should be at least 10 characters", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateFrom = try {
                dateFormat.parse(leaveFromDate)
            } catch (e: Exception) {
                null
            }
            val dateTo = try {
                dateFormat.parse(leaveToDate)
            } catch (e: Exception) {
                null
            }

            if (dateFrom == null || dateTo == null) {
                Snackbar.make(view, "Invalid Date Format. Use DD/MM/YYYY", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (dateFrom.after(dateTo)) {
                Snackbar.make(view, "From Date can never be greater than To Date", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // All validations passed
            val leaveID = generateFirestoreLeaveID(storedUserName)
            val leaveModel = LeaveModel(
                leaveID = leaveID,
                leaveApplicantUserName = storedUserName,
                leaveDesc = leaveDesc,
                leaveFromDate = leaveFromDate,
                leaveType = leaveType,
                leaveToDate = leaveToDate,
                leaveStatus = "Pending",
                leaveMedicalFileUrl = "Null",
                leaveApplicantName = storedActualName,
                leaveApplicantType = storedUserType

            )
            viewModel.setLeaveApply(storedUserName, leaveID, leaveModel)
        }


        //handling leaveSetStatus from viewmodel
        viewModel.leaveSetStatus.observe(viewLifecycleOwner) { status ->
            if(status){
                Snackbar.make(view,"Leave Applied Successfully", Snackbar.LENGTH_SHORT).show()
                view.findNavController().navigate(R.id.action_leaveRequestScreenFragment_to_leaveScreenFragment)
            }
            else{
                Snackbar.make(view,"Failed to Apply Leave", Snackbar.LENGTH_SHORT).show()
                view.findNavController().navigate(R.id.action_leaveRequestScreenFragment_to_leaveScreenFragment)
            }
        }

        backIVBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_leaveRequestScreenFragment_to_leaveScreenFragment)
        }

        return view
    }

    fun generateFirestoreLeaveID(userName: String): String {
        val dateFormat = SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault())
        val formattedDate = dateFormat.format(Date())
        val id = "${userName}_$formattedDate"
        return id
    }

}