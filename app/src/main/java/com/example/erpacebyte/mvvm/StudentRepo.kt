package com.example.erpacebyte.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.erpacebyte.models.AttendanceModel
import com.example.erpacebyte.models.EventModel
import com.example.erpacebyte.models.LeaveModel
import com.example.erpacebyte.models.RemarkModel
import com.example.erpacebyte.models.StudentDetailModel
import com.example.erpacebyte.models.TimeTableModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class StudentRepo {

    private val firestore = FirebaseFirestore.getInstance()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getStudentDetails(userName: String, onResult: (StudentDetailModel?) -> Unit){
        _isLoading.value = true
        firestore.collection("StudentDetail").document(userName).get()
            .addOnSuccessListener { document ->
                _isLoading.value = false
                if (document.exists()){
                    val studentDetail = document.toObject(StudentDetailModel::class.java)
                    onResult(studentDetail)
                }
                else {
                    Log.e("FirestoreStudent", "Student Detail not found")
                    onResult(null)
                }
            }
            .addOnFailureListener { e ->
                _isLoading.value = false
                Log.e("FirestoreStudent", "Error fetching student details", e)
                onResult(null)
            }

    }

    fun getMonthEventList(monthName:String, onResult:(List<EventModel>?) -> Unit) {
        _isLoading.value = true
        firestore.collection("Events").document(monthName).collection("All").get()
            .addOnSuccessListener { documents->
                _isLoading.value = false
                val eventsList = mutableListOf<EventModel>()

                for (document in documents) {
                    val event = document.toObject(EventModel::class.java)
                    eventsList.add(event)
                }

                onResult(eventsList) // Return the list
            }
            .addOnFailureListener { e ->
                _isLoading.value = false
                Log.e("FirestoreEvents", "Error fetching event details", e)
                onResult(null)
            }
    }

    fun getMonthAttendance(userName:String,monthYearName:String,onResult:(List<AttendanceModel>?) -> Unit){
        _isLoading.value=true
        firestore.collection("StudentAttendance").document(userName).collection(monthYearName).get()
            .addOnSuccessListener { documents ->
                _isLoading.value = false
                val attendanceList = mutableListOf<AttendanceModel>()

                for (document in documents){
                    val attendance = document.toObject(AttendanceModel::class.java)
                    attendanceList.add(attendance)
                }
                onResult(attendanceList)
            }
            .addOnFailureListener { e ->
                _isLoading.value = false
                Log.e("FirestoreAttendance", "Error fetching attendance details", e)
                onResult(null)
            }
    }

    fun setLeaveData(userName: String, leaveID: String, leaveModel: LeaveModel, onResult: (Boolean) -> Unit) {
        _isLoading.value = true

        val userDocRef = firestore.collection("Leave").document(userName)

        //  Ensure the parent document exists
        userDocRef.set(mapOf("createdAt" to FieldValue.serverTimestamp()), SetOptions.merge())    //For Making Document Visible to get() cals of Firestore
            .addOnSuccessListener {
                //  Write to subcollection
                userDocRef.collection("All").document(leaveID)
                    .set(leaveModel)
                    .addOnSuccessListener {
                        _isLoading.value = false
                        Log.d("FirestoreLeave", "Leave data successfully written!")
                        onResult(true)
                    }
                    .addOnFailureListener { e ->
                        _isLoading.value = false
                        Log.e("FirestoreLeave", "Error writing leave data", e)
                        onResult(false)
                    }
            }
            .addOnFailureListener { e ->
                _isLoading.value = false
                Log.e("FirestoreLeave", "Error creating parent user document", e)
                onResult(false)
            }
    }

    fun getLeaveList(userName:String,onResult:(List<LeaveModel>?) -> Unit){
        _isLoading.value=true
        firestore.collection("Leave").document(userName).collection("All").get()
            .addOnSuccessListener { documents ->
                _isLoading.value = false
                val leaveList = mutableListOf<LeaveModel>()

                for (document in documents){
                    val leave = document.toObject(LeaveModel::class.java)
                    leaveList.add(leave)
                }
                onResult(leaveList)
            }
            .addOnFailureListener { e ->
                _isLoading.value = false
                Log.e("FirestoreLeave", "Error fetching leave details", e)
                onResult(null)
            }
    }

    fun getTimetableList(className: String,onResult: (List<TimeTableModel>?) -> Unit){
        _isLoading.value = true
        firestore.collection("TimeTable").document(className).collection("All").get()
            .addOnSuccessListener { documents ->
                _isLoading.value = false
                val timeTableList = mutableListOf<TimeTableModel>()

                for (document in documents){
                    val timeTable = document.toObject(TimeTableModel::class.java)
                    timeTableList.add(timeTable)
                }
                onResult(timeTableList)
            }
            .addOnFailureListener { e ->
            _isLoading.value = false
            Log.e("FirestoreLeave", "Error fetching leave details", e)
            onResult(null)

        }
    }

    fun getRemarksList(userName:String, onResult: (List<RemarkModel>?) -> Unit){
        _isLoading.value = true
        firestore.collection("Remarks").document(userName).collection("All").get()
            .addOnSuccessListener { documents ->
                _isLoading.value = false
                val remarksList = mutableListOf<RemarkModel>()

                for (document in documents){
                    val remark = document.toObject(RemarkModel::class.java)
                    remarksList.add(remark)
                }
                onResult(remarksList)
            }
            .addOnFailureListener { e ->
                _isLoading.value = false
                Log.e("FirestoreRemark", "Error fetching remarks details", e)
                onResult(null)

            }
    }
}