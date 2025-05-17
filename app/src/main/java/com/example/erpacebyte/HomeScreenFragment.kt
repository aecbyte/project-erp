package com.example.erpacebyte

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.erpacebyte.adapters.TimeTableRVAdapter
import com.example.erpacebyte.models.TimeTableModel
import com.example.erpacebyte.mvvm.ERPUserMainViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlin.math.ceil

class HomeScreenFragment : Fragment() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView : NavigationView
    private lateinit var userActualNameTV : TextView
    private lateinit var userEmailTV: TextView
    private lateinit var userContactNoTV: TextView
    private lateinit var userRegsNo: TextView
    private lateinit var attendanceProgressBar: ProgressBar
    private lateinit var resultProgressBar: ProgressBar
    private lateinit var attendanceTV: TextView
    private lateinit var resultTV: TextView
    private lateinit var timetableRV: RecyclerView
    private lateinit var noTimeTableTV: TextView

    private lateinit var progressBar: ProgressBar
    private lateinit var viewBG: View

    private lateinit var sharedPreferences: SharedPreferences
    private var storedUserName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home_screen, container, false)

        //Initializing Views
        userActualNameTV = view.findViewById(R.id.userName)
        userEmailTV= view.findViewById(R.id.userEmail)
        userContactNoTV= view.findViewById(R.id.userContact)
        userRegsNo= view.findViewById(R.id.userRegsNo)
        attendanceProgressBar= view.findViewById(R.id.progressBarAttendence)
        resultProgressBar= view.findViewById(R.id.progressBarResult)
        attendanceTV = view.findViewById(R.id.attendanceTV)
        resultTV = view.findViewById(R.id.resultTV)
        progressBar = view.findViewById(R.id.progressBar)
        viewBG = view.findViewById(R.id.viewBG)
        noTimeTableTV = view.findViewById(R.id.noTimeTableTV)
        timetableRV=view.findViewById(R.id.timetableRV)

        //ViewModel
        val viewModel = ViewModelProvider(this)[ERPUserMainViewModel::class.java]

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        storedUserName = sharedPreferences.getString("USERNAME", "") ?: ""

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

        //handling student Details
        viewModel.student.observe(viewLifecycleOwner) { studentModel ->
            if(studentModel != null){
                Log.d("HomeScreen", "Student Data: $studentModel")
                userActualNameTV.text = studentModel.studentActualName
                saveUserToPrefs(studentModel.studentActualName)
                userEmailTV.text = studentModel.studentEmail
                userRegsNo.text = studentModel.studentRegsNo
                userContactNoTV.text = studentModel.studentContactNo
                val attendancePercentage = lub((studentModel.currPresentAttendance.toFloat()/studentModel.currTotalAttendance.toFloat())*100)
                attendanceTV.text = attendancePercentage.toString()+"%"
                attendanceProgressBar.progress = attendancePercentage
                val resultPercentage = lub((studentModel.latestObtainedMarks.toFloat()/studentModel.latestTotalMarks.toFloat())*100)
                resultTV.text = resultPercentage.toString()+"%"
                resultProgressBar.progress = resultPercentage

                //setting timetable
                val userClassSection = studentModel.currClassSection.replace(" ","")
                viewModel.fetchTimeTableList(userClassSection)
            }
            else{
                Snackbar.make(view,"Server Error. Try Again Later", Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.timetableList.observe(viewLifecycleOwner) { timetableList->
            if(timetableList.isNullOrEmpty()){
                noTimeTableTV.visibility = View.VISIBLE
                timetableRV.visibility= View.GONE
            }
            else{
                noTimeTableTV.visibility = View.GONE
                timetableRV.visibility= View.VISIBLE
                timetableRV.layoutManager = LinearLayoutManager(requireContext())
                val rvAdapter= TimeTableRVAdapter(requireContext(),timetableList)
                timetableRV.adapter = rvAdapter
            }
        }



        drawerLayout = view.findViewById(R.id.drawerNavigation)
        navigationView = view.findViewById(R.id.navView)
        val btnOpenDrawer = view.findViewById<ImageView>(R.id.menuIcon)

        btnOpenDrawer.setOnClickListener {
            if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.navTimeTable -> {
                    Toast.makeText(requireContext(),"Clicked on TimeTable",Toast.LENGTH_SHORT).show()
                }
                R.id.navFees -> {

                    view.findNavController().navigate(R.id.action_homeScreenFragment_to_paymentScreenFragment)
                }
                R.id.navCalendar -> {

                    view.findNavController().navigate(R.id.action_homeScreenFragment_to_calenderScreenFragment)
                }
                R.id.navServiceReq -> {
                    Toast.makeText(requireContext(),"Clicked on Service Request",Toast.LENGTH_SHORT).show()
                }
                R.id.navLeave -> {
                    view.findNavController().navigate(R.id.action_homeScreenFragment_to_leaveScreenFragment)
                }
                R.id.navEvent -> {
                    view.findNavController().navigate(R.id.action_homeScreenFragment_to_calenderScreenFragment)
                }
                R.id.navTransport -> {
                    Toast.makeText(requireContext(),"Clicked on Transport",Toast.LENGTH_SHORT).show()
                }
                R.id.navRemark -> {
                    view.findNavController().navigate(R.id.action_homeScreenFragment_to_remarkScreenFragment)
                }
                R.id.navLogout -> {
                    Toast.makeText(requireContext(),"Clicked on Logout",Toast.LENGTH_SHORT).show()
                }
                R.id.navResult -> {
                    view.findNavController().navigate(R.id.action_homeScreenFragment_to_resultScreenFragment)
                }
                R.id.navAttendence -> {
                    view.findNavController().navigate(R.id.action_homeScreenFragment_to_attendenceScreenFragment)
                }
            }
            drawerLayout.closeDrawers()
            true
        }


        return view
    }

    private fun lub(value: Float): Int {
        return ceil(value).toInt()
    }

    private fun saveUserToPrefs(userName: String) {
        val editor = sharedPreferences.edit()
        editor.putString("USERACTUALNAME", userName)
        editor.apply()
    }

}