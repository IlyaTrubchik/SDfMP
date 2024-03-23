package com.example.movies.ui.viewModels

import android.app.Activity
import android.content.ContentValues.TAG
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import android.widget.Toast
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewModelScope

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

data class LoginUiState(
    val isLoading:Boolean = false,
    val errorMessage:String?= null,
    val isUserLoggedIn:Boolean = false,
)

class LoginViewModel: ViewModel() {
    private lateinit var auth: FirebaseAuth
    var uiState by mutableStateOf(LoginUiState())
        private set

    init{
//        val auth:FirebaseAuth = Firebase.auth
 //       val user = auth.currentUser
//        if(user != null)
 //       {
 //           uiState = uiState.copy(isUserLoggedIn = true)
 //       }
    }
    fun login(email: String, password: String) {
        viewModelScope.launch {
            val auth: FirebaseAuth = Firebase.auth
            if(email!="" && password!="") {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            uiState = uiState.copy(isUserLoggedIn = true)

                        } else {
                            // If sign in fails, display a message to the user.
                            uiState = uiState.copy(
                                isUserLoggedIn = false,
                                errorMessage = "Failed to log in"
                            )

                        }
                    }
            }else{
                uiState = uiState.copy(
                    isUserLoggedIn = false,
                    errorMessage = "Failed to log in"
                )
            }
        }

    }

}
fun logOut()
{
    val auth:FirebaseAuth = Firebase.auth
    val user = auth.currentUser
    if(user != null)
    {
        auth.signOut();
    }
}


