package com.example.eats.util

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class ExperimentManager {
    private val remoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        
        remoteConfig.setDefaultsAsync(mapOf(
            "enable_new_matching_algorithm" to false,
            "show_restaurant_photos" to true,
            "max_group_size" to 10,
            "enable_chat_feature" to false
        ))
    }

    fun fetchAndActivate(onComplete: () -> Unit) {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { onComplete() }
    }

    fun isFeatureEnabled(featureName: String): Boolean {
        return remoteConfig.getBoolean(featureName)
    }

    fun getIntValue(key: String): Int {
        return remoteConfig.getLong(key).toInt()
    }
} 