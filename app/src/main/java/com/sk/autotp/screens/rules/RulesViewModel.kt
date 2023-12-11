package com.sk.autotp.screens.rules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sk.autotp.data.Repository
import com.sk.autotp.data.Rule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RulesViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val rules = repository.getRules()

    fun updateRuleStatus(rule: Rule, enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRuleStatus(rule.id, enabled)
        }
    }
}