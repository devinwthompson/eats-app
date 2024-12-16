package com.example.eats.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent

class AnalyticsTracker(private val analytics: FirebaseAnalytics) {
    fun trackScreenView(screenName: String) {
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
    }

    fun trackRestaurantVote(restaurantId: String, liked: Boolean) {
        analytics.logEvent("restaurant_vote") {
            param("restaurant_id", restaurantId)
            param("vote_type", if (liked) "like" else "dislike")
        }
    }

    fun trackGroupCreation(groupSize: Int) {
        analytics.logEvent("group_created") {
            param("group_size", groupSize.toLong())
        }
    }

    fun trackMatchFound(groupId: String, restaurantId: String) {
        analytics.logEvent("match_found") {
            param("group_id", groupId)
            param("restaurant_id", restaurantId)
        }
    }

    fun trackError(errorType: String, message: String) {
        analytics.logEvent("app_error") {
            param("error_type", errorType)
            param("error_message", message)
        }
    }

    fun trackUserEngagement(eventName: String, params: Map<String, Any> = emptyMap()) {
        analytics.logEvent(eventName) {
            params.forEach { (key, value) ->
                when (value) {
                    is String -> param(key, value)
                    is Long -> param(key, value)
                    is Double -> param(key, value)
                    is Boolean -> param(key, value)
                }
            }
        }
    }
} 