package com.example.erpacebyte.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.erpacebyte.models.StudentDetailModel
import com.example.erpacebyte.models.UserModel
import com.google.firebase.firestore.FirebaseFirestore

class UsersRepo {

    private val firestore = FirebaseFirestore.getInstance()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getUserDetails(userName: String , onResult: (UserModel?) -> Unit){
        _isLoading.value = true
        firestore.collection("Users").document(userName)
            .get()
            .addOnSuccessListener { document->
                _isLoading.value = false

                if (document.exists()){
                    val user = document.toObject(UserModel::class.java)
                    onResult(user)
                }
                else {
                    Log.e("Firestore", "User not found")
                    onResult(null)
                }
            }
            .addOnFailureListener { e ->
                _isLoading.value = false
                Log.e("Firestore", "Error fetching user details", e)
                onResult(null)
            }
    }




}