package com.example.eats.feedback

import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.languageid.LanguageIdentification
import kotlinx.coroutines.tasks.await

class FeedbackClassifier {
    private val languageIdentifier = LanguageIdentification.getClient()
    private val categories = mapOf(
        "bug" to listOf("crash", "error", "not working", "broken"),
        "feature" to listOf("would be nice", "suggestion", "please add", "need"),
        "ui" to listOf("layout", "design", "looks", "interface"),
        "performance" to listOf("slow", "lag", "freeze", "loading")
    )

    suspend fun classifyFeedback(feedback: UserFeedback): FeedbackCategory {
        val language = detectLanguage(feedback.content)
        val normalizedContent = feedback.content.toLowerCase()
        
        return when {
            containsKeywords(normalizedContent, categories["bug"]!!) -> FeedbackCategory.BUG
            containsKeywords(normalizedContent, categories["feature"]!!) -> FeedbackCategory.FEATURE
            containsKeywords(normalizedContent, categories["ui"]!!) -> FeedbackCategory.UI
            containsKeywords(normalizedContent, categories["performance"]!!) -> FeedbackCategory.PERFORMANCE
            else -> FeedbackCategory.GENERAL
        }
    }

    private suspend fun detectLanguage(text: String): String {
        return languageIdentifier.identifyLanguage(text).await()
    }

    private fun containsKeywords(text: String, keywords: List<String>): Boolean {
        return keywords.any { text.contains(it) }
    }
}

enum class FeedbackCategory {
    BUG, FEATURE, UI, PERFORMANCE, GENERAL
} 