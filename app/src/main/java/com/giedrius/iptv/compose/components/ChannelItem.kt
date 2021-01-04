package com.giedrius.iptv.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giedrius.iptv.R
import com.giedrius.iptv.compose.ui.IptvTheme

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IptvTheme {
        ChannelItem()
    }
}

@Composable
fun ChannelItem() {
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