package com.example.eats.util

import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashTracker {
    private val crashlytics = FirebaseCrashlytics.getInstance()
    private val breadcrumbLimit = 100

    fun logBreadcrumb(
        action: String,
        data: Map<String, Any> = emptyMap()
    ) {
        crashlytics.log("$action: ${data.entries.joinToString()}")
    }

    fun trackUserFlow(screen: String, action: String) {
        logBreadcrumb("navigation", mapOf(
            "screen" to screen,
            "action" to action,
            "timestamp" to System.currentTimeMillis()
        ))
    }

    fun trackNetworkCall(
        url: String,
        method: String,
        responseCode: Int
    ) {
        logBreadcrumb("network", mapOf(
            "url" to url,
            "method" to method,
            "response_code" to responseCode
        ))
    }

    fun trackError(
        error: Throwable,
        context: Map<String, Any> = emptyMap()
    ) {
        logBreadcrumb("error", context + mapOf(
            "error_type" to error.javaClass.simpleName,
            "message" to (error.message ?: "Unknown error")
        ))
        crashlytics.recordException(error)
    }
} 