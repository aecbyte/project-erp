package com.example.erpacebyte.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.erpacebyte.models.AttendanceModel
import com.example.erpacebyte.models.EventModel
import com.example.erpacebyte.models.LeaveModel
import com.example.erpacebyte.models.RemarkModel
import com.example.erpacebyte.models.StudentDetailModel
import com.example.erpacebyte.models.TimeTableModel
import com.example.erpacebyte.models.UserModel

class ERPUserMainViewModel(): ViewModel() {

    private val usersRepo = UsersRepo()
    private val studentRepo = StudentRepo()

    private val _user = MutableLiveData<UserModel?>()
    val user: LiveData<UserModel?> get() = _user

    val isLoading: LiveData<Boolean> get() = usersRepo.isLoading

    private val _student = MutableLiveData<StudentDetailModel?>()
    val student: LiveData<StudentDetailModel?> get() = _student

    private val _eventOfMonthList = MutableLiveData<List<EventModel>?>()
    val eventOfMonthList: LiveData<List<EventModel>?> get() = _eventOfMonthList

    private val _attendanceOfMonthList = MutableLiveData<List<AttendanceModel>?>()
    val attendanceOfMonthList: LiveData<List<AttendanceModel>?> get() = _attendanceOfMonthList

    private val _leaveList = MutableLiveData<List<LeaveModel>?>()
    val leaveList: LiveData<List<LeaveModel>?> get() = _leaveList

    private val _timeTableList = MutableLiveData<List<TimeTableModel>?>()
    val timetableList: LiveData<List<TimeTableModel>?> get() = _timeTableList

    private val _remarkList = MutableLiveData<List<RemarkModel>?>()
    val remarkList: LiveData<List<RemarkModel>?> get() = _remarkList

    private val _leaveSetStatus = MutableLiveData<Boolean>()
    val leaveSetStatus: LiveData<Boolean> get() = _leaveSetStatus

    val isLoadingStudentRepo: LiveData<Boolean> get() = studentRepo.isLoading

    fun fetchUser(userName: String) {
        usersRepo.getUserDetails(userName) { userDet->
            _user.value = userDet
        }
    }

    fun fetchStudentDetail(userName: String){
        studentRepo.getStudentDetails(userName) { studDet ->
            _student.value = studDet
        }
    }

    fun fetchMonthEventList(monthName: String){
        studentRepo.getMonthEventList(monthName) { listEvent ->
            _eventOfMonthList.value = listEvent
        }
    }

    fun fetchMonthAttendance(userName: String,monthYearName: String){
        studentRepo.getMonthAttendance(userName,monthYearName) { listAttendance ->
            _attendanceOfMonthList.value = listAttendance
        }

    }

    fun setLeaveApply(userName: String, leaveID: String, leaveModel: LeaveModel){
        studentRepo.setLeaveData(userName,leaveID,leaveModel) { status ->
            _leaveSetStatus.value = status
        }
    }

    fun fetchUserLeave(userName: String){
        studentRepo.getLeaveList (userName) { leaveList  ->
            _leaveList.value = leaveList
        }

    }

    fun fetchTimeTableList(className: String){
        studentRepo.getTimetableList(className) { timeTableList ->
            _timeTableList.value = timeTableList
        }

    }

    fun fetchRemarkList(userName: String){
        studentRepo.getRemarksList(userName) { remarkList ->
            _remarkList.value = remarkList
        }
    }
}