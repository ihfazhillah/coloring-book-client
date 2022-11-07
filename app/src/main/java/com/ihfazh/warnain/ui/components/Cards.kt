package com.ihfazh.warnain.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ihfazh.warnain.domain.Category

@Composable
fun ImageCard(
//    url: String,
//    contentDescription: String,
//    title: String,
    category: Category,
    modifier: Modifier = Modifier,
    onClick: (Category) -> Unit = {}
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = {onClick.invoke(category)}),
        elevation = 2.dp,

        ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(category.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = category.title,
                contentScale = ContentScale.Inside,
                modifier = Modifier.fillMaxWidth()
                    .heightIn(120.dp)
            )
            Text(
                category.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(8.dp)
            )
        }

    }
}