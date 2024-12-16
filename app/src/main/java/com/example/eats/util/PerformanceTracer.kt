package com.example.eats.util

import com.google.firebase.perf.metrics.Trace

class PerformanceTracer {
    private val monitor = PerformanceMonitor()

    fun traceRestaurantLoad(count: Int): Trace {
        return monitor.startTrace("restaurant_load").apply {
            putMetric("restaurant_count", count.toLong())
            putAttribute("source", "api")
        }
    }

    fun traceGroupMatching(): Trace {
        return monitor.startTrace("group_matching").apply {
            putAttribute("algorithm_version", "v2")
        }
    }

    fun traceUserInteraction(action: String): Trace {
        return monitor.startTrace("user_interaction").apply {
            putAttribute("action_type", action)
            putAttribute("screen", getCurrentScreen())
        }
    }

    fun traceDataSync(): Trace {
        return monitor.startTrace("data_sync").apply {
            putMetric("last_sync_time", System.currentTimeMillis())
        }
    }

    private fun getCurrentScreen(): String {
        return try {
            val activity = // Get current activity reference
            activity?.javaClass?.simpleName ?: "Unknown"
        } catch (e: Exception) {
            "Unknown"
        }
    }
} 