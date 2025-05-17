package com.example.erpacebyte.models

data class AttendanceModel(
    val userName : String ="",
    val fullDate:String = "",
    val attendanceStatus:String = "",
){
    constructor(): this("","","")
}
