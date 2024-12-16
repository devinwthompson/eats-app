package com.example.eats.experiments

class ExperimentConfig(private val experimentManager: ExperimentManager) {
    // UI Experiments
    fun getCardStyle(): String = 
        if (experimentManager.isFeatureEnabled("new_card_style")) "modern" else "classic"
    
    fun shouldShowRatingsBadge(): Boolean =
        experimentManager.isFeatureEnabled("show_ratings_badge")
    
    // Feature Experiments
    fun getMatchingAlgorithm(): String =
        if (experimentManager.isFeatureEnabled("enable_new_matching_algorithm")) "v2" else "v1"
    
    fun getChatExperience(): ChatExperience {
        return when {
            experimentManager.isFeatureEnabled("enable_group_chat") -> ChatExperience.GROUP
            experimentManager.isFeatureEnabled("enable_direct_chat") -> ChatExperience.DIRECT
            else -> ChatExperience.DISABLED
        }
    }
    
    // Performance Experiments
    fun getPreloadCount(): Int =
        experimentManager.getIntValue("restaurant_preload_count")
    
    fun getRefreshInterval(): Long =
        experimentManager.getLongValue("refresh_interval_minutes") * 60 * 1000
}

enum class ChatExperience {
    DISABLED, DIRECT, GROUP
} 