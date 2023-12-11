package com.sk.autotp.screens.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sk.autotp.data.Contact
import com.sk.autotp.data.Repository
import com.sk.autotp.data.Rule
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.UUID

class EditorViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted val ruleId: UUID?,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(ruleId: UUID?): EditorViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            ruleId: UUID?,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(ruleId) as T
            }
        }
    }

    init {
        if (ruleId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val existingRule = repository.getRule(ruleId)
                _rule.update { existingRule }
            }
        }
    }

    private val _rule: MutableStateFlow<Rule> = MutableStateFlow(
        Rule(
            id = UUID.randomUUID(),
            createdAt = Instant.now(),
            enabled = true,
            title = "",
            keywords = "",
            contact = Contact("", ""),
            notify = false,
            prefix = ""
        )
    )

    val rule get() = _rule.asStateFlow()

    val isEditing get() = ruleId != null

    fun onStatusChanged(enabled: Boolean) {
        _rule.update {
            it.copy(enabled = enabled)
        }
    }

    fun onNotifyChanged(notify: Boolean) {
        _rule.update {
            it.copy(notify = notify)
        }
    }

    fun onTitleChanged(title: String) {
        _rule.update {
            it.copy(title = title)
        }
    }

    fun onKeywordsChanged(keywords: String) {
        _rule.update {
            it.copy(keywords = keywords)
        }
    }

    fun onPrefixChanged(prefix: String) {
        _rule.update {
            it.copy(prefix = prefix)
        }
    }

    fun onSaveClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertRule(rule.value)
        }
    }

    fun onDeleteClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRule(rule.value)
        }
    }

    fun onContactNameChanged(name: String) {
        _rule.update {
            it.copy(contact = it.contact.copy(name = name))
        }
    }

    fun onContactNumberChanged(number: String) {
        _rule.update {
            it.copy(contact = it.contact.copy(number = number))
        }
    }
}