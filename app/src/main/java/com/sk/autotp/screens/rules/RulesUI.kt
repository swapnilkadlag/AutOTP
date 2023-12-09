package com.sk.autotp.screens.rules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SwitchAccessShortcutAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.sk.autotp.R
import com.sk.autotp.data.Rule
import com.sk.autotp.ui.Margin
import com.sk.autotp.ui.MasterSwitch
import com.sk.autotp.ui.NoContent
import com.sk.autotp.ui.Padding
import com.sk.autotp.ui.Separator
import com.sk.autotp.ui.VerticalSpace

@Preview(device = Devices.PIXEL_4)
@Composable
private fun ContentPreview() {
    Surface {
        Content(
            rules = emptyList(),
            masterSwitchChecked = false,
            onMasterSwitchCheckedChange = {},
            onHistoryButtonClick = {},
            onAddButtonClick = {},
            onSettingsButtonClick = {},
            onAppIconClick = {},
            onRuleClick = {},
            onRuleStatusChange = { _, _ -> },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    onHistoryButtonClick: () -> Unit,
    onSettingsButtonClick: () -> Unit,
    onAppIconClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.rules),
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = onAppIconClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_autotp),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }, actions = {
            IconButton(onClick = onHistoryButtonClick) {
                Icon(
                    imageVector = Icons.Outlined.History,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
            IconButton(onClick = onSettingsButtonClick) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
        })
}

@Composable
fun Content(
    rules: List<Rule>,
    masterSwitchChecked: Boolean,
    onMasterSwitchCheckedChange: (Boolean) -> Unit,
    onHistoryButtonClick: () -> Unit,
    onSettingsButtonClick: () -> Unit,
    onAppIconClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    onRuleClick: (Rule) -> Unit,
    onRuleStatusChange: (Rule, Boolean) -> Unit,
) {
    val scrollState = rememberLazyListState()
    val fabExtended = remember { derivedStateOf { !scrollState.canScrollBackward } }
    Scaffold(
        topBar = {
            TopAppBar(
                onHistoryButtonClick = onHistoryButtonClick,
                onSettingsButtonClick = onSettingsButtonClick,
                onAppIconClick = onAppIconClick
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = fabExtended.value,
                text = {
                    Text(
                        text = stringResource(id = R.string.add_rule),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                icon = { Icon(imageVector = Icons.Outlined.Add, contentDescription = "") },
                onClick = { onAddButtonClick() },
            )
        },
    ) { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            if (rules.isEmpty()) {
                NoContent(
                    icon = Icons.Outlined.SwitchAccessShortcutAdd,
                    title = stringResource(id = R.string.no_rules_added),
                    subtitle = stringResource(id = R.string.tap_plus_to_get_started),
                )
            } else {
                MasterSwitch(
                    title = stringResource(id = R.string.forward_messages),
                    checked = masterSwitchChecked,
                    onCheckedChange = onMasterSwitchCheckedChange,
                )
                Rules(
                    scrollState = scrollState,
                    rules = rules,
                    onRuleClick = onRuleClick,
                    onRuleStatusChange = onRuleStatusChange
                )
            }
        }
    }
}

@Composable
private fun Rules(
    scrollState: LazyListState,
    rules: List<Rule>,
    onRuleClick: (Rule) -> Unit,
    onRuleStatusChange: (Rule, Boolean) -> Unit,
) {
    LazyColumn(modifier = Modifier.padding(), state = scrollState) {
        items(rules) { rule ->
            Rule(rule = rule, onRuleClick = { onRuleClick(rule) }, onRuleStatusChange = onRuleStatusChange)
        }
        item {
            VerticalSpace(height = Margin.FabHeight)
        }
    }
}

@Composable
private fun Rule(
    rule: Rule,
    onRuleClick: () -> Unit,
    onRuleStatusChange: (Rule, Boolean) -> Unit,
) {
    Column(modifier = Modifier.clickable { onRuleClick() }) {
        Row(
            modifier = Modifier.padding(Padding.Large),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = rule.title, style = MaterialTheme.typography.titleMedium)
                VerticalSpace(height = Margin.VerySmall)
                Text(text = rule.contact.name, style = MaterialTheme.typography.bodyMedium)
            }
            Switch(
                checked = rule.enabled,
                onCheckedChange = { onRuleStatusChange(rule, it) }
            )
        }
        Separator()
    }
}
