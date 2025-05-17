package com.example.erpacebyte.models

data class TimeTableModel(
    val scheduleID:String = "",
    val classDuration:String="",
    val teacherName:String="",
    val subjectName:String="",
    val roomNo:String="",
    val teacherImgUrl: String="",
    val teacherUserName: String = "",
    val day: List<String> = emptyList(),
    val classSect: String=""

    ){
    constructor():this("","","","","","","",emptyList(),"")
}
