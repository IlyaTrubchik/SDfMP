package com.example.movies.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movies.ui.viewModels.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.filter



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onUserLogIn: () -> Unit // Navigate to next screen
){
    val loginValue = remember{mutableStateOf("")};
    val passwordValue  =  remember{mutableStateOf("")};
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }


        Column(
            modifier = Modifier.fillMaxSize() ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
                    .weight(0.2f)
            ) {
                SnackbarHost(
                    hostState = snackbarHostState.value,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }

            Column(
                modifier = Modifier.padding(20.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .weight(0.8f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextField(value = loginValue.value,
                    onValueChange = {newText-> loginValue.value = newText},
                    textStyle = TextStyle(fontSize=25.sp),
                    modifier = Modifier.padding(vertical = 10.dp),
                    label = {
                        Text(
                            text = "Email",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 4.dp)
                        )
                    }
                )
                TextField(value = passwordValue.value,
                    onValueChange = {newText-> passwordValue.value = newText},
                    textStyle = TextStyle(fontSize=25.sp),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.padding(vertical = 10.dp),
                    label = {
                        Text(
                            text = "Password",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 4.dp)
                        )
                    }

                )
                Button(
                    onClick = {viewModel.login(loginValue.value,passwordValue.value); },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                    shape = RoundedCornerShape (
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp
                    ),
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .height(50.dp)
                ) {
                    Text("Login",fontSize = 25.sp,color = Color.White)
                }
            }
        }
        viewModel.uiState.errorMessage?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                snackbarHostState.value.showSnackbar(message = errorMessage,duration = SnackbarDuration.Short)
            }
        }


    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val currentOnUserLogIn by rememberUpdatedState(onUserLogIn)
    LaunchedEffect(viewModel, lifecycle)  {
        // Whenever the uiState changes, check if the user is logged in and
        // call the `onUserLogin` event when `lifecycle` is at least STARTED
        snapshotFlow { viewModel.uiState }
            .filter { it.isUserLoggedIn }
            .flowWithLifecycle(lifecycle)
            .collect {
                currentOnUserLogIn()
            }
    }
}


