package com.sk.autotp.screens.history

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sk.autotp.data.Contact
import com.sk.autotp.screens.ComposableFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.util.UUID

@AndroidEntryPoint
class HistoryFragment : ComposableFragment() {

    private val viewModel by viewModels<HistoryViewModel>()

    @Composable
    override fun BoxScope.FragmentContent() {

        val logs by viewModel.logs.collectAsState(initial = emptyList())

        val showDeleteButton by remember {
            derivedStateOf { logs.isNotEmpty() }
        }

        Content(
            logs = logs,
            showDeleteButton = showDeleteButton,
            onDeleteAllButtonClick = ::onDeleteButtonClick,
            onBackIconClick = ::onBackClicked,
            onDeleteButtonClick = viewModel::deleteLog
        )
    }

    private fun onDeleteButtonClick() {
        viewModel.deleteLogs()
    }

    private fun onBackClicked() {
        findNavController().popBackStack()
    }

}