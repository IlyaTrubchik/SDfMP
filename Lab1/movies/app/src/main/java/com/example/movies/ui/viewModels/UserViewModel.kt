package com.example.movies.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.User
import com.example.movies.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

data class UserUiState(
    val email:String? ="",
    val name:String? ="",
    var surName:String? ="",
    val telephone:String? ="",
    val address:String? ="",
    val postcode:String? = "",
    val country:String? = "",
    val city:String? = "",
    val age:String? = "",
    val sex:String? = ""
)


class UserViewModel:ViewModel() {
    var uiState by mutableStateOf(UserUiState())
            private set
    private val repository:UserRepository = UserRepository()
    private var auth: FirebaseAuth = Firebase.auth
    private val user = auth.currentUser
    private val userId = user?.uid
    init{
        loadUserData()
    }
    private fun loadUserData(){
        viewModelScope.launch {
               repository.getUserInfo(userId.toString()) { user ->
                   uiState = uiState.copy(
                       name = user.name, surName = user.surname, email = user.email,
                       telephone = user.telephone, address = user.address, postcode = user.postcode,
                       country = user.country, city = user.city, age = user.age, sex = user.sex
                   )
               }
        }
    }
    fun deleteAccount(){
        val auth:FirebaseAuth = Firebase.auth
        val user = auth.currentUser
        if(user != null)
        {
            auth.signOut();
        }
        viewModelScope.launch {
            repository.deleteUserById(userId!!);
            user?.delete();
        }
    }
    fun logOut(){
        viewModelScope.launch {
            auth.signOut()
        }
    }
}