package com.example.erpacebyte

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.erpacebyte.adapters.TimeTableRVAdapter
import com.example.erpacebyte.models.TimeTableModel
import com.google.android.material.navigation.NavigationView

class HomeScreenFragment : Fragment() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView : NavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home_screen, container, false)

        val dummyTimeTableList= listOf(
            TimeTableModel("English","Walter White","9:00  - 10:00","Room No-5"),
                    TimeTableModel("Hindi","Walter White","10:00  - 11:00","Room No-5"),
        TimeTableModel("Physics","Walter White","10:00  - 11:00","Room No-5"),
        TimeTableModel("Chemistry","Walter White","10:00  - 11:00","Room No-5"),
        TimeTableModel("Painting","Walter White","10:00  - 11:00","Room No-5"),
        )




        val timetableRV = view.findViewById<RecyclerView>(R.id.timetableRV)
        timetableRV.layoutManager = LinearLayoutManager(requireContext())
        val rvAdapter= TimeTableRVAdapter(requireContext(),dummyTimeTableList)
        timetableRV.adapter = rvAdapter

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
                    view.findNavController().navigate(R.id.action_homeScreenFragment_to_leaveRequestScreenFragment)
                }
                R.id.navEvent -> {
                    view.findNavController().navigate(R.id.action_homeScreenFragment_to_calenderScreenFragment)
                }
                R.id.navTransport -> {
                    Toast.makeText(requireContext(),"Clicked on Transport",Toast.LENGTH_SHORT).show()
                }
                R.id.navRemark -> {
                    Toast.makeText(requireContext(),"Clicked on Remark",Toast.LENGTH_SHORT).show()
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

}