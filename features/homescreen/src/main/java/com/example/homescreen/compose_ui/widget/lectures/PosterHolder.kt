package com.example.homescreen.compose_ui.widget.lectures

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.basecomponent.theme.GRAY_EFEFEF
import com.example.homescreen.R

@Composable
@Preview
fun PosterHolder(
    imageUrl : String = "",
    onShare : ()->Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.91f)
            .padding(horizontal = 24.dp, vertical = 14.dp)
        ,
        shape = RoundedCornerShape(15.dp),
        elevation = 3.dp,
        backgroundColor = GRAY_EFEFEF
    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ){
            Image(
                painter = painterResource(id = R.drawable.share_logo),
                contentDescription = "",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(15.dp)
            )
            Image(
                painter = rememberAsyncImagePainter(
                    if(imageUrl.contains("https")) imageUrl
                            else imageUrl.replace("http", "https"),
                    placeholder = painterResource(id = com.example.basecomponent.R.drawable.ic_cari_ceramah_dim)
                ),
                contentDescription = "splash_background",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onShare()
                    }
                ,
                contentScale = ContentScale.Fit
            )
        }
    }
}