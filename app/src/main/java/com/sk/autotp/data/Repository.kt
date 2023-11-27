package com.sk.autotp.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class Repository @Inject constructor(
    private val db: AutOTPDatabase,
    private val scope: CoroutineScope,
) {

    fun upsertRule(rule: Rule) {
        scope.launch { db.ruleDao.upsert(rule) }
    }

    fun deleteRule(rule: Rule) {
        scope.launch { db.ruleDao.delete(rule) }
    }

    suspend fun getRule(ruleId: UUID): Rule = db.ruleDao.get(ruleId)

    fun getRules() = db.ruleDao.getAll()

    suspend fun getEnabledRules(): List<Rule> = db.ruleDao.getAllEnabled()

    fun updateRuleStatus(ruleId: UUID, enabled: Boolean) {
        scope.launch { db.ruleDao.updateStatus(ruleId, enabled) }
    }

    fun insertLog(log: Log) {
        scope.launch { db.logDao.insert(log) }
    }

    fun getLogs() = db.logDao.getAll()
}