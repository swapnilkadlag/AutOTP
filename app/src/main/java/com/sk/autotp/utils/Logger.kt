package com.sk.autotp.utils

import android.util.Log

object Logger {

    private const val TAG = "AutOTP-Log"

    fun logDebug(className: String, functionName: String, message: String? = null) {
        Log.d(TAG, "$className -> $functionName -> $message")
    }
}