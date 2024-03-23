package com.example.movies.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.movies.ui.viewModels.MovieViewModel
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movies.ui.viewModels.MovieListItem
import java.io.ByteArrayInputStream


@Composable
fun MovieScreen(
    panelOnClicks: panelOnClicks,
    movieId: String,
    viewModel: MovieViewModel = viewModel(),
) {
    viewModel.getAllImages(movieId)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box() {
            Column(modifier = Modifier.verticalScroll(rememberScrollState()))
            {
                ImageSlider(
                    imageList = viewModel.uiState.images,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                TextContent(
                    name = "Название",
                    content = viewModel.uiState.name
                )


                TextContent(
                    name = "Страна выпуска:",
                    content = viewModel.uiState.country
                )


                TextContent(
                    name = "Жанры",
                    content = viewModel.uiState.genre
                )


                TextContent(
                    name = "Описание",
                    content = viewModel.uiState.description
                )


            }
            NavigationPanel(
                panelOnClicks = panelOnClicks,
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .height(72.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun ImageSlider(modifier: Modifier, imageList: List<ByteArray>?) {
    LazyRow(
        modifier = modifier
    ) {
        if (imageList != null) {
            items(imageList) { imageBytes ->
                ImageSlide(imageBytes)
            }
        }
    }
}

@Composable
fun ImageSlide(imageBytes: ByteArray) {
    val imageBitmap = BitmapFactory.decodeStream(ByteArrayInputStream(imageBytes)).asImageBitmap()
    Image(
        bitmap = imageBitmap,
        contentDescription = null,
        modifier = Modifier
            .size(400.dp)
            .padding(horizontal = 8.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomEnd = 10.dp,
                    bottomStart = 10.dp,
                )
            ),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun TextContent(name: String, content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {
            Divider(
                modifier = Modifier
                    .padding(start = 0.dp, end = 0.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                color = Color.LightGray,
                thickness = 1.dp
            )
            Text(
                text = name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 4.dp)
            )
            Text(
                text = content,
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 4.dp)
            )
            Divider(
                modifier = Modifier
                    .padding(start = 0.dp, end = 0.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                color = Color.LightGray,
                thickness = 1.dp
            )
        }
    }
}

@Composable
fun MovieList(imageList: List<ByteArray>?) {
    LazyColumn {
        if (imageList != null) {
            items(imageList) { image ->
                MovieView(imageBytes = image)
            }
        }
    }
}

@Composable
fun MovieView(imageBytes: ByteArray) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            val imageBitmap =
                BitmapFactory.decodeStream(ByteArrayInputStream(imageBytes)).asImageBitmap()
            Image(
                bitmap = imageBitmap,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(horizontal = 4.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}