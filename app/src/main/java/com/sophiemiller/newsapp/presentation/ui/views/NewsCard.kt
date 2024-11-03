package com.sophiemiller.newsapp.presentation.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sophiemiller.newsapp.R
import com.sophiemiller.newsapp.data.entities.ArticlePreview

@Composable
fun NewsCard(articlePreview: ArticlePreview, onArticleClicked: (url: String?) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onArticleClicked.invoke(articlePreview.link)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            //Source
            Row {
                articlePreview.sourceIcon?.let {
                    // Source image from URL
                    AsyncImage(
                        model = it,
                        contentDescription = articlePreview.title ?: "Title",
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp),
                        placeholder = painterResource(R.drawable.ic_logo), // Optional placeholder image
                        error = painterResource(R.drawable.ic_logo) // Optional error image
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                }

                //Source Title
                Text(
                    text = articlePreview.sourceName ?: "Source",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            // Image from URL
            AsyncImage(
                model = articlePreview.imageUrl,
                contentDescription = articlePreview.title ?: "Title",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                placeholder = painterResource(R.drawable.ic_logo), // Optional placeholder image
                error = painterResource(R.drawable.ic_logo) // Optional error image
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Title Text
            Text(
                text = articlePreview.title ?: "Title",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Description Text
            Text(
                text = articlePreview.description ?: "Title",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
