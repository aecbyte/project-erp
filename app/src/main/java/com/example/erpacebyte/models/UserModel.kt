package com.example.erpacebyte.models

data class UserModel(
    val userName: String = "",
    val name: String = "",
    val userPassword: String = "",
    val userEmail: String = "",
    val userContact: String = "",
    val userRegsNo: String = ""
) {
    // No-argument constructor required by Firestore
    constructor() : this("", "", "", "", "", "")
}

