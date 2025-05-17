package com.example.erpacebyte.models

data class StudentDetailModel(
    val userName: String = "",
    val studentActualName: String = "",
    val studentEmail: String = "",
    val studentContactNo: String = "",
    val studentRegsNo: String = "",
    val currClassSection: String = "",
    val currPresentAttendance: Int = 0,
    val currTotalAttendance: Int = 0,
    val latestTotalMarks: Float = 0f,
    val latestObtainedMarks: Float = 0f,
    val currClassRollNo:String = "",
    val studentImageUrl:String = "",
    val studentFatherName:String = "",
)
{
    constructor() : this("", "", "", "", "", "",0,0,0f,0f,"","","")
}