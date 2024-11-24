package com.example.kotlin_vk2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.request.ImageRequest

const val url = "https://cataas.com/cat"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                ImageCard("1")
                ImageCard("2")
                ImageCard("3")
                ImageCard("4")
                ImageCard("5")
            }
        }
    }
}


@Composable
fun ImageCard(id:String) {
    val context = LocalContext.current
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(context).data(url).memoryCacheKey(url+id)
            .diskCacheKey(url+id).build(),
        contentDescription = "cat",
        contentScale = ContentScale.FillWidth,
    ) {
        val state by painter.state.collectAsState()
        when (state) {
            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent(modifier =  Modifier
                    .border(BorderStroke(10.dp, Color.Blue), RectangleShape))
            }

            is AsyncImagePainter.State.Error -> {
                TextButton(onClick = { painter.restart() },  modifier = Modifier.size(200.dp).border(BorderStroke(10.dp, Color.Blue), RectangleShape)) {
                    Text("Retry")
                }
            }

            else -> {
                CircularProgressIndicator( modifier = Modifier.size(200.dp))
            }
        }
    }
}
