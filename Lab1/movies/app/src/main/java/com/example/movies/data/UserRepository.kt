package com.example.movies.data

import com.google.firebase.database.FirebaseDatabase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserRepository {
    private val database = FirebaseDatabase.getInstance("https://moviesapplication-973c9-default-rtdb.europe-west1.firebasedatabase.app/")


     fun getUserInfo(userId:String,callback:(User)-> Unit)
     {
            var user: User = User()
            val refToInfo = database.getReference("users").child(userId).child("userInfo")
            refToInfo.get().addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.exists()) {
                    val taskSnapshot = task.result
                    user = taskSnapshot.getValue(User::class.java)!!
                    callback(user)
                }
            }
     }
    fun deleteUserById(userId:String)
    {
        val refToUser = database.getReference("users").child(userId);
        refToUser.removeValue();
    }
    fun setUserInfo(userId:String,userInfo:User)
    {
        val refToInfo = database.getReference("users").child(userId).child("userInfo").setValue(userInfo)
    }
}