package com.sk.autotp.screens.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DeleteSweep
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sk.autotp.R
import com.sk.autotp.data.Contact
import com.sk.autotp.data.Log
import com.sk.autotp.ui.HorizontalSpace
import com.sk.autotp.ui.Margin
import com.sk.autotp.ui.NoContent
import com.sk.autotp.ui.Padding
import com.sk.autotp.ui.Separator
import com.sk.autotp.ui.VerticalSpace
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.UUID

@Preview(device = Devices.PIXEL_4)
@Composable
private fun ContentPreview() {
    Surface {
        Content(
            logs = emptyList(),
            showDeleteButton = false,
            onDeleteAllButtonClick = {},
            onBackIconClick = {},
            onDeleteButtonClick = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    showDeleteButton: Boolean,
    onDeleteButtonClick: () -> Unit,
    onBackIconClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.history),
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackIconClick,
                content = { Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "") }
            )
        }, actions = {
            if (showDeleteButton) {
                IconButton(onClick = onDeleteButtonClick) {
                    Icon(
                        imageVector = Icons.Outlined.DeleteSweep,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
        }
    )
}

@Composable
fun Content(
    logs: List<Log>,
    showDeleteButton: Boolean,
    onDeleteAllButtonClick: () -> Unit,
    onBackIconClick: () -> Unit,
    onDeleteButtonClick: (Log) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                showDeleteButton = showDeleteButton,
                onDeleteButtonClick = onDeleteAllButtonClick,
                onBackIconClick = onBackIconClick
            )
        },
    ) { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            if (logs.isEmpty()) {
                NoContent(
                    icon = Icons.Outlined.History,
                    title = stringResource(id = R.string.no_history_found),
                )
            } else {
                Logs(logs = logs, onDeleteButtonClick = onDeleteButtonClick)
            }
        }
    }
}

@Composable
private fun Logs(logs: List<Log>, onDeleteButtonClick: (Log) -> Unit) {
    LazyColumn {
        items(logs) { log ->
            Log(log = log, onDeleteButtonClick = { onDeleteButtonClick(log) })
        }
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
private fun Log() {
    Log(
        log = Log(
            id = UUID.randomUUID(),
            message = "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface ",
            sentOn = Instant.now(),
            contact = Contact(
                name = "Contact",
                number = "98798342234",
            )
        ),
        onDeleteButtonClick = {},
    )
}

@Composable
private fun Log(log: Log, onDeleteButtonClick: () -> Unit) {
    val sentOn = LocalDateTime.ofInstant(log.sentOn, ZoneOffset.systemDefault())
    val dtf = DateTimeFormatter.ofPattern("E, MMM dd yyyy, hh:mm a")
    Column {
        Row(
            modifier = Modifier.padding(Padding.Large),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_autotp),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    HorizontalSpace(width = Margin.Small)
                    Text(
                        modifier = Modifier.weight(1f),
                        text = dtf.format(sentOn),
                        style = MaterialTheme.typography.labelMedium
                    )
                    IconButton(onClick = onDeleteButtonClick) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "",
                        )
                    }
                }
                VerticalSpace(height = Margin.Small)
                Text(text = log.message, style = MaterialTheme.typography.bodyLarge)
                VerticalSpace(height = Margin.Small)
                Text(
                    text = "${log.contact.name} : ${log.contact.number}",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
        Separator()
    }
}
