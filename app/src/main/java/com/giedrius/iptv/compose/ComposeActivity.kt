package com.giedrius.iptv.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giedrius.iptv.R
import com.giedrius.iptv.compose.ui.IptvTheme

class ComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IptvTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    NewsStory()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IptvTheme {
        NewsStory()
    }
}

@Composable
fun NewsStory() {
    MaterialTheme() {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                imageResource(R.drawable.header),
                modifier = Modifier
                    .preferredSize(50.dp)
                    .clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )
            Text(
                "A day wandering through the sandhills",
                style = MaterialTheme.typography.subtitle2,
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(PaddingValues(4.dp))
            )

        }
    }
}

@Composable
fun PhotographerCard() {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = { /* Ignoring onClick */ })
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.preferredSize(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            // Image goes here
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text("Alfred Sisley", fontWeight = FontWeight.Bold)
            Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Preview
@Composable
fun PhotographerCardPreview() {
    IptvTheme {
        PhotographerCard()
    }
}