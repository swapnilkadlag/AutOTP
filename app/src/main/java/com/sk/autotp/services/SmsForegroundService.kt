package com.sk.autotp.services

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.provider.Telephony
import com.sk.autotp.R
import com.sk.autotp.utils.Logger
import com.sk.autotp.utils.NotificationHelper

class SmsForegroundService : Service() {

    companion object {

        private const val SERVICE_ID = 90001

        val isRunning get() = _isRunning

        @Volatile
        private var _isRunning: Boolean = false

        fun setRunning(running: Boolean) = synchronized(this) {
            _isRunning = running
        }
    }

    inner class LocalBinder : Binder()

    private var smsBroadcastReceiver: SmsBroadcastReceiver? = null

    private val binder = LocalBinder()

    override fun onCreate() {
        super.onCreate()
        Logger.logDebug(this::class.java.simpleName, "onCreate")
        setRunning(true)

        NotificationHelper.createNotificationChannel(this)
        val notification = NotificationHelper.createNotification(
            context = this,
            text = getString(R.string.listening_for_incoming_messages),
        )
        startForeground(SERVICE_ID, notification)

        smsBroadcastReceiver = SmsBroadcastReceiver()
        val filter = IntentFilter()
        filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        registerReceiver(smsBroadcastReceiver, filter)
    }

    override fun onDestroy() {
        unregisterReceiver(smsBroadcastReceiver)
        setRunning(false)
        Logger.logDebug(this::class.java.simpleName, "onDestroy")
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.logDebug(this::class.java.simpleName, "onStartCommand")
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        Logger.logDebug(this::class.java.simpleName, "onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Logger.logDebug(this::class.java.simpleName, "onUnbind")
        return super.onUnbind(intent)
    }
}