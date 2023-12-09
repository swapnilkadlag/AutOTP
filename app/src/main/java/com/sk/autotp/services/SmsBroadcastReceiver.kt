package com.sk.autotp.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.sk.autotp.data.Repository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SmsBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var smsHandler: SmsHandler

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        for (message in smsMessages) {
            scope.launch {
                val rules = repository.getEnabledRules()
                smsHandler.handleSMS(rules, message)
            }
        }
    }
}