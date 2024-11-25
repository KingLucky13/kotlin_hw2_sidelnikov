package com.example.kotlin_vk2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.request.ImageRequest

const val url = "https://cataas.com/cat"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ids = listOf("1","2","3","4","5")
        setContent{
            LazyColumn{
                items(ids) {id -> ImageCard(id) }
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
        contentDescription = stringResource(R.string.image_description),
        modifier = Modifier.padding(10.dp)
    ) {
        val state by painter.state.collectAsState()
        when (state) {
            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent(modifier =  Modifier
                    .fillMaxWidth()
                    ,contentScale = ContentScale.Crop)
            }

            is AsyncImagePainter.State.Error -> {
                TextButton(onClick = { painter.restart() },  modifier = Modifier
                    .size(200.dp)
                    .border(BorderStroke(10.dp, Color.Blue), CircleShape)) {
                    Text(stringResource(R.string.retry_text))
                }
            }

            else -> {
                CircularProgressIndicator( modifier = Modifier.size(200.dp))
            }
        }
    }
}
