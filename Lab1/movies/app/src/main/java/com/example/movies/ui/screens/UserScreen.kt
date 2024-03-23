package com.example.movies.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movies.ui.viewModels.UserViewModel

@Composable
fun UserScreen(
    panelOnClicks: panelOnClicks,
    viewModel:UserViewModel = viewModel(),
    onLogOut:()->Unit,
    onDelete:()->Unit
){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.LightGray,
        shape = MaterialTheme.shapes.medium
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .align(Alignment.TopCenter)
            ){
                Text("Name: ${viewModel.uiState.name}", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                Divider(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
                Text("Surname: ${viewModel.uiState.surName}", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                Divider(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
                Text("Age: ${viewModel.uiState.age}", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                Divider(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
                Text("Sex: ${viewModel.uiState.sex}", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                Divider(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
                Text("Country: ${viewModel.uiState.country}", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                Divider(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
                Text("City: ${viewModel.uiState.city}", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                Divider(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
                Text("Address: ${viewModel.uiState.address}", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                Divider(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
                Text("postcode: ${viewModel.uiState.postcode}", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp));
                Divider(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
                Text("Email: ${viewModel.uiState.email}", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                Divider(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    color = Color.LightGray,
                    thickness = 1.dp
                )

                Text("Telephone: ${viewModel.uiState.telephone}", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                Row(modifier = Modifier.padding(vertical = 10.dp))
                {
                    Button(
                        onClick = {viewModel.deleteAccount(); onDelete() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                        shape = RoundedCornerShape (
                            topStart = 10.dp,
                            topEnd = 10.dp,
                            bottomStart = 10.dp,
                            bottomEnd = 10.dp
                        ),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .weight(0.3f)
                    ) {
                        Text(text = "Delete account", color = Color.White)
                    }
                    Button(
                        onClick = { onLogOut() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                        shape = RoundedCornerShape (
                            topStart = 10.dp,
                            topEnd = 10.dp,
                            bottomStart = 10.dp,
                            bottomEnd = 10.dp
                        ),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .weight(0.3f)
                    ) {
                        Text(text = "Log Out", color = Color.White)
                    }
                }

            }
            NavigationPanel(
                panelOnClicks = panelOnClicks(panelOnClicks.onProfileClick,panelOnClicks.onMovieClick,panelOnClicks.onFavoritesClick),
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .height(72.dp)
                    .clip(RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 0.dp, bottomEnd = 0.dp))
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview
@Composable
fun UserPreview(){
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.LightGray,
            shape = MaterialTheme.shapes.medium
        ) {
            Box(modifier = Modifier.fillMaxSize()){
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(16.dp)
                        .align(Alignment.TopCenter)
                ){
                    Text("Name: Илья", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                    Divider(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Text("Surname: Трубчик", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                    Divider(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Text("Age: 18", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                    Divider(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Text("Sex: мужской", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                    Divider(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Text("Country: Беларусь", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                    Divider(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Text("City: Минск", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                    Divider(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Text("Address: ул.Первомайская, д.20", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                    Divider(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Text("postcode: 223828", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp));
                    Divider(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Text("Email: ilya.thrubchik@Mail.ru", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                    Divider(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Text("Telephone: +375445718607", fontSize = 25.sp,modifier = Modifier.padding(vertical = 8.dp))
                }
                TestNavigationPanel(
                    panelOnClicks = panelOnClicks({},{},{}),
                    modifier = Modifier
                        .background(Color.LightGray)
                        .fillMaxWidth()
                        .height(72.dp)
                        .clip(RoundedCornerShape(topStart = 36.dp, bottomStart = 36.dp, topEnd = 36.dp, bottomEnd = 36.dp))
                        .align(Alignment.BottomCenter)
                )
            }
    }

}
@Composable
fun TestNavigationPanel(
    panelOnClicks: panelOnClicks,
    modifier: Modifier)
{
    Row(
        modifier = modifier,
    ) {
        Button(
            onClick = {},
            shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp,
                bottomStart = 30.dp,
            ),
            modifier = Modifier
                .weight(1f)
                .background(Color.Gray)
                .fillMaxHeight()

        ) {
            // Кнопка 1
        }
        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(Color.DarkGray)
        )
        Button(
            onClick = {},
            shape = RoundedCornerShape(
               topStart = 0.dp,
               topEnd = 0.dp,
               bottomStart = 0.dp,
               bottomEnd = 0.dp
            ),
            modifier = Modifier
                .weight(1f)
                .background(Color.Gray)
                .fillMaxHeight()
        ) {
            // Кнопка 2
        }
        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(Color.DarkGray)
        )
        Button(
            onClick =  {},
            shape = RoundedCornerShape (
                topStart = 0.dp,
                topEnd = 30.dp,
                bottomStart = 0.dp,
                bottomEnd = 30.dp
                ),
            modifier = Modifier
                .weight(1f)
                .background(Color.Gray)
                .fillMaxHeight()
        ) {
            // Кнопка 3
        }
    }
}


