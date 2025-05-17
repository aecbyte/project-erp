package com.example.erpacebyte.models

data class EventModel(
    val eventName:String = "",
    val eventFullDate:String = "",
    val eventType:String = "",
    val eventID:String = "",
    val eventMonthName:String = "",
){
    constructor(): this("","","","","")
}
