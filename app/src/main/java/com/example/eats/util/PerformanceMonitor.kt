package com.example.eats.util

import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.metrics.Trace

class PerformanceMonitor {
    private val performance = FirebasePerformance.getInstance()

    fun startTrace(traceName: String): Trace {
        return performance.newTrace(traceName).apply { start() }
    }

    fun measureNetworkRequest(url: String) {
        performance.newHttpMetric(url, "GET").apply {
            start()
            // Your network call here
            stop()
        }
    }

    fun trackScreenLoad(screenName: String) {
        startTrace("screen_load_$screenName").apply {
            putAttribute("screen", screenName)
            // Your screen loading logic here
            stop()
        }
    }
} 