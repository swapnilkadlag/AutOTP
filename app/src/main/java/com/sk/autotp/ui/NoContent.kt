package com.sk.autotp.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NoContent() {
    NoContent(
        icon = Icons.Outlined.History,
        title = "Preview title",
        subtitle = "Preview sub-title"
    )
}

@Composable
fun NoContent(
    icon: ImageVector,
    title: String? = null,
    subtitle: String? = null,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(128.dp),
            imageVector = icon,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary,
        )
        if (!title.isNullOrEmpty()) {
            VerticalSpace(height = Margin.VeryLarge)
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        }
        if (!subtitle.isNullOrEmpty()) {
            VerticalSpace(height = Margin.Small)
            Text(text = subtitle, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
