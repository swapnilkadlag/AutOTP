package com.sk.autotp.screens.editor

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sk.autotp.screens.ComposableFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditorFragment : ComposableFragment() {

    private val args: EditorFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelAssistedFactory: EditorViewModel.Factory

    private val viewModel: EditorViewModel by viewModels {
        EditorViewModel.provideFactory(viewModelAssistedFactory, args.ruleId)
    }

    private fun onDeleteClicked() {
        viewModel.onDeleteClicked()
        findNavController().popBackStack()
    }

    private fun onSaveClicked() {
        viewModel.onSaveClicked()
        findNavController().popBackStack()
    }

    private fun onBackClicked() {
        findNavController().popBackStack()
    }

    @Composable
    override fun BoxScope.FragmentContent() {

        val rule by viewModel.rule.collectAsState()

        val showSaveButton by remember {
            derivedStateOf {
                rule.title.isNotEmpty()
                        && rule.keywords.isNotEmpty()
                        && rule.contact.name.isNotEmpty()
                        && rule.contact.number.isNotEmpty()
            }
        }

        Content(
            masterSwitchChecked = rule.enabled,
            onMasterSwitchCheckedChange = viewModel::onStatusChanged,
            showSaveButton = showSaveButton,
            title = rule.title,
            onTitleChanged = viewModel::onTitleChanged,
            keywords = rule.keywords,
            onKeywordsChanged = viewModel::onKeywordsChanged,
            showDeleteButton = viewModel.isEditing,
            onDeleteButtonClick = ::onDeleteClicked,
            onSaveButtonClick = ::onSaveClicked,
            notify = rule.notify,
            onNotifyChanged = viewModel::onNotifyChanged,
            prefix = rule.prefix,
            onPrefixChanged = viewModel::onPrefixChanged,
            onBackIconClick = ::onBackClicked,
            contactName = rule.contact.name,
            onContactNameChanged = viewModel::onContactNameChanged,
            contactNumber = rule.contact.number,
            onContactNumberChanged = viewModel::onContactNumberChanged,
        )
    }
}