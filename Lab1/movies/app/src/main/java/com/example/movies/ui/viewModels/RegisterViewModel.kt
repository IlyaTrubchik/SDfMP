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

data class registerUiState(
    val isRegistered:Boolean = false,
    val isLoading:Boolean = false,
    val errorMessage:String?= null,
)
class RegisterViewModel : ViewModel(){
    var uiState by mutableStateOf(registerUiState())
        private set
    private lateinit var auth: FirebaseAuth
    private val repository = UserRepository()

    fun register(user: User, pass:String)
    {
        viewModelScope.launch {
            val auth: FirebaseAuth = Firebase.auth
            if(user.email != "" && pass != "") {
                auth.createUserWithEmailAndPassword(user.email!!, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            uiState = uiState.copy(isRegistered = true)
                            repository.setUserInfo(auth.currentUser?.uid!!, user)
                        } else {
                            // If sign in fails, display a message to the user.
                            uiState =
                                uiState.copy(isRegistered = false, errorMessage = "Register Failed")

                        }
                    }
            }else{
                uiState = uiState.copy(isRegistered = false, errorMessage = "Register Failed")
            }

        }
    }
}