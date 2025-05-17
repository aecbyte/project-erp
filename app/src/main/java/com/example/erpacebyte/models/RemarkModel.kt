package com.example.erpacebyte.models

data class RemarkModel(
    val senderUserName : String = "",
    val senderName : String = "",
    val receiverUserName : String = "",
    val remarkID : String = "",           // senderUserName_receiverUserName_remarkDateFormatted
    val remarkDescription : String = "",
    val remarkDate : String = "",
){
    constructor() : this("", "", "", "", "", "")
}
