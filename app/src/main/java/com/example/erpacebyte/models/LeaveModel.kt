package com.example.erpacebyte.models


data class LeaveModel(
    val leaveID:String = "",
    val leaveDesc:String = "",
    val leaveType: String = "",
    val leaveFromDate : String = "",
    val leaveToDate: String="",
    val leaveStatus: String="",
    val leaveApplicantUserName:String="",
    val leaveMedicalFileUrl:String="",
    val leaveApplicantName:String="",
    val leaveApplicantType: String=""
){
    constructor():this("","","","","","","","","","")
}
