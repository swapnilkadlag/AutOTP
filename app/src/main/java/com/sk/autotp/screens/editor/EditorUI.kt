package com.sk.autotp.screens.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sk.autotp.R
import com.sk.autotp.ui.HorizontalSpace
import com.sk.autotp.ui.Margin
import com.sk.autotp.ui.MasterSwitch
import com.sk.autotp.ui.Padding
import com.sk.autotp.ui.Separator
import com.sk.autotp.ui.TextField
import com.sk.autotp.ui.VerticalSpace

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
                text = stringResource(id = R.string.rule),
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackIconClick,
                content = { Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "") }
            )
        },
        actions = {
            if (showDeleteButton) {
                IconButton(onClick = onDeleteButtonClick) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
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
    masterSwitchChecked: Boolean,
    onMasterSwitchCheckedChange: (Boolean) -> Unit,
    title: String,
    onTitleChanged: (String) -> Unit,
    showSaveButton: Boolean,
    keywords: String,
    onKeywordsChanged: (String) -> Unit,
    notify: Boolean,
    onNotifyChanged: (Boolean) -> Unit,
    prefix: String,
    onPrefixChanged: (String) -> Unit,
    contactName: String,
    onContactNameChanged: (String) -> Unit,
    contactNumber: String,
    onContactNumberChanged: (String) -> Unit,
    showDeleteButton: Boolean,
    onDeleteButtonClick: () -> Unit,
    onBackIconClick: () -> Unit,
    onSaveButtonClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val fabExtended = remember { derivedStateOf { !scrollState.canScrollBackward } }
    Scaffold(
        topBar = {
            TopAppBar(
                showDeleteButton = showDeleteButton,
                onDeleteButtonClick = onDeleteButtonClick,
                onBackIconClick = onBackIconClick
            )
        },
        floatingActionButton = {
            if (showSaveButton) {
                ExtendedFloatingActionButton(
                    expanded = fabExtended.value,
                    text = { Text(text = "Save", style = MaterialTheme.typography.bodyMedium) },
                    icon = { Icon(imageVector = Icons.Outlined.Save, contentDescription = "") },
                    onClick = { onSaveButtonClick() },
                )
            }
        },
    ) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .verticalScroll(scrollState)
        ) {
            MasterSwitch(
                title = stringResource(id = R.string.forward_messages),
                checked = masterSwitchChecked,
                onCheckedChange = onMasterSwitchCheckedChange,
            )
            VerticalSpace(height = Margin.Medium)
            TextField(
                value = title,
                onValueChange = onTitleChanged,
                label = stringResource(id = R.string.title),
            )
            TextField(
                value = keywords,
                onValueChange = onKeywordsChanged,
                label = stringResource(id = R.string.keywords),
            )
            Row(
                modifier = Modifier.padding(horizontal = Padding.Large),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(imageVector = Icons.Outlined.Info, contentDescription = "")
                HorizontalSpace(width = Margin.Small)
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.add_multiple_keywords),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            VerticalSpace(height = Margin.Small)
            TextField(
                value = prefix,
                onValueChange = onPrefixChanged,
                label = stringResource(id = R.string.prefix),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HorizontalSpace(width = Margin.VerySmall)
                Checkbox(checked = notify, onCheckedChange = onNotifyChanged)
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.notify_after_forwarding),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            VerticalSpace(height = Margin.Medium)
            Separator()
            VerticalSpace(height = Margin.Medium)
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(Padding.Large),
                text = stringResource(id = R.string.forward_to),
                style = MaterialTheme.typography.titleMedium
            )
            TextField(
                value = contactName,
                onValueChange = onContactNameChanged,
                label = stringResource(id = R.string.name),
            )
            TextField(
                value = contactNumber,
                onValueChange = onContactNumberChanged,
                label = stringResource(id = R.string.number),
            )
            VerticalSpace(height = Margin.FabHeight)
        }
    }
}
