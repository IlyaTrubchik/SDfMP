package com.example.movies.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movies.data.User
import com.example.movies.ui.viewModels.RegisterViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onRegister: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }

    val passwordValue = remember { mutableStateOf("") };
    val email = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") };
    val postcode = remember { mutableStateOf("") };
    val age = remember { mutableStateOf("") };
    val country = remember { mutableStateOf("") };
    val sex = remember { mutableStateOf("") };
    val city = remember { mutableStateOf("") };
    val surname = remember { mutableStateOf("") };
    val address = remember { mutableStateOf("") };
    val telephone = remember { mutableStateOf("") };
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
                    .weight(0.1f)
            ) {
                SnackbarHost(
                    hostState = snackbarHostState.value,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RoundedCornerShape(0.dp))
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
                    .weight(0.9f)
                    .fillMaxWidth()
            ) {
                TextField(
                    value = email.value,
                    onValueChange = { newText -> email.value = newText },
                    textStyle = TextStyle(fontSize = 25.sp),
                    modifier = Modifier.padding(vertical = 5.dp),
                    label = {
                        Text(
                            text = "email",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 4.dp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                TextField(
                    value = passwordValue.value,
                    onValueChange = { newText -> passwordValue.value = newText },
                    textStyle = TextStyle(fontSize = 25.sp),
                    modifier = Modifier.padding(vertical = 5.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    label = {
                        Text(
                            text = "Password",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 4.dp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                TextField(value = name.value,
                    onValueChange = { newText -> name.value = newText },
                    textStyle = TextStyle(fontSize = 25.sp),
                    modifier = Modifier.padding(vertical = 5.dp),
                    label = {
                        Text(
                            text = "Name",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 4.dp)
                        )
                    }
                )
                TextField(value = surname.value,
                    onValueChange = { newText -> surname.value = newText },
                    textStyle = TextStyle(fontSize = 25.sp),
                    modifier = Modifier.padding(vertical = 5.dp),
                    label = {
                        Text(
                            text = "Surname",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 4.dp)
                        )
                    }
                )
                TextField(
                    value = age.value,
                    onValueChange = { newText -> age.value = newText },
                    textStyle = TextStyle(fontSize = 25.sp),
                    modifier = Modifier.padding(vertical = 5.dp),
                    label = {
                        Text(
                            text = "Age",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 4.dp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                TextField(value = sex.value,
                    onValueChange = { newText -> sex.value = newText },
                    textStyle = TextStyle(fontSize = 25.sp),
                    modifier = Modifier.padding(vertical = 5.dp),
                    label = {
                        Text(
                            text = "Sex",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 4.dp)
                        )
                    }
                )
                TextField(
                    value = telephone.value,
                    onValueChange = { newText -> telephone.value = newText },
                    textStyle = TextStyle(fontSize = 25.sp),
                    modifier = Modifier.padding(vertical = 5.dp),
                    label = {
                        Text(
                            text = "Telephone",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 4.dp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                TextField(value = country.value,
                    onValueChange = { newText -> country.value = newText },
                    textStyle = TextStyle(fontSize = 25.sp),
                    modifier = Modifier.padding(vertical = 5.dp),
                    label = {
                        Text(
                            text = "Country",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 4.dp)
                        )
                    }
                )
                TextField(value = city.value,
                    onValueChange = { newText -> city.value = newText },
                    textStyle = TextStyle(fontSize = 25.sp),
                    modifier = Modifier.padding(vertical = 5.dp),
                    label = {
                        Text(
                            text = "City",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 4.dp)
                        )
                    }
                )
                TextField(value = address.value,
                    onValueChange = { newText -> address.value = newText },
                    textStyle = TextStyle(fontSize = 25.sp),
                    modifier = Modifier.padding(vertical = 5.dp),
                    label = {
                        Text(
                            text = "Address",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 4.dp)
                        )
                    }
                )
                TextField(
                    value = postcode.value,
                    onValueChange = { newText -> postcode.value = newText },
                    textStyle = TextStyle(fontSize = 25.sp),
                    modifier = Modifier.padding(vertical = 5.dp),
                    label = {
                        Text(
                            text = "Postcode",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 4.dp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Button(
                    onClick = {
                        viewModel.register(
                            User(
                                name = name.value,
                                surname = surname.value,
                                email = email.value,
                                city = city.value,
                                age = age.value,
                                country = country.value,
                                sex = sex.value,
                                telephone = telephone.value,
                                address = address.value,
                                postcode = postcode.value
                            ), passwordValue.value
                        );
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                    shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp
                    ),
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .height(50.dp)
                ) {
                    Text("Register", fontSize = 25.sp, color = Color.White)
                }
                Button(
                    onClick = { onRegister() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                    shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp
                    ),
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 20.dp)
                        .height(50.dp)
                ) {
                    Text("LogIn", fontSize = 25.sp, color = Color.White)
                }
            }
        }
    }


    viewModel.uiState.errorMessage?.let { errorMessage ->
        LaunchedEffect(errorMessage) {
            snackbarHostState.value.showSnackbar(message = errorMessage, duration = SnackbarDuration.Short)
        }
    }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val currentOnUserRegister by rememberUpdatedState(onRegister)
    LaunchedEffect(viewModel, lifecycle) {
        // Whenever the uiState changes, check if the user is logged in and
        // call the `onUserRegister` event when `lifecycle` is at least STARTED
        snapshotFlow { viewModel.uiState }
            .filter { it.isRegistered }
            .flowWithLifecycle(lifecycle)
            .collect {
                if(viewModel.uiState.isRegistered) {
                    currentOnUserRegister()
                }
            }
    }
}

fun isBlankFields(vararg fields: String): Boolean {
    for (field in fields) {
        if (field.isBlank()) return true;
    }
    return false;
}