package com.sk.autotp.services

import android.telephony.SmsManager
import android.telephony.SmsMessage
import com.sk.autotp.data.Log
import com.sk.autotp.data.Repository
import com.sk.autotp.data.Rule
import java.time.Instant
import java.util.UUID
import javax.inject.Inject

class SmsHandler @Inject constructor(
    private val smsManager: SmsManager,
    private val repository: Repository,
) {
    fun handleSMS(rules: List<Rule>, sms: SmsMessage) {
        val messageBody = sms.messageBody
        val applicableRules = rules.filter { rule ->
            SmsFilter.isApplicable(messageBody, rule)
        }
        for (rule in applicableRules) {
            sendSMS(rule, sms)
        }
    }

    private fun sendSMS(rule: Rule, sms: SmsMessage) {
        val message = if (rule.prefix.isNotEmpty()) {
            "${rule.prefix} - ${sms.messageBody}"
        } else {
            sms.messageBody
        }
        smsManager.sendTextMessage(sms.originatingAddress, null, message, null, null)
        repository.insertLog(
            Log(
                id = UUID.randomUUID(),
                message = message,
                sentOn = Instant.now(),
                contact = rule.contact,
            )
        )
    }
}