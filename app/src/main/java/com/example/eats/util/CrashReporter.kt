package com.example.eats.util

import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineExceptionHandler

class CrashReporter {
    private val crashlytics = FirebaseCrashlytics.getInstance()

    fun setUserIdentifier(userId: String) {
        crashlytics.setUserId(userId)
    }

    fun logException(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }

    fun setCustomKey(key: String, value: String) {
        crashlytics.setCustomKey(key, value)
    }

    // Coroutine exception handler for crash reporting
    val coroutineExceptionHandler = CoroutineExceptionHandler { _: CoroutineContext, throwable: Throwable ->
        logException(throwable)
    }
} 