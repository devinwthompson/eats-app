package com.example.eats.feedback

import com.google.cloud.language.v1.Document
import com.google.cloud.language.v1.LanguageServiceClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SentimentAnalyzer {
    private val positiveWords = setOf("great", "awesome", "love", "excellent")
    private val negativeWords = setOf("bad", "terrible", "hate", "poor")

    suspend fun analyzeSentiment(feedback: UserFeedback): SentimentScore {
        return withContext(Dispatchers.Default) {
            val text = feedback.content.toLowerCase()
            val positiveCount = positiveWords.count { text.contains(it) }
            val negativeCount = negativeWords.count { text.contains(it) }
            
            val score = when {
                positiveCount > negativeCount -> 1.0
                negativeCount > positiveCount -> -1.0
                else -> 0.0
            }
            
            SentimentScore(
                score = score,
                magnitude = (positiveCount + negativeCount).toDouble()
            )
        }
    }
}

data class SentimentScore(
    val score: Double,  // -1.0 to 1.0
    val magnitude: Double  // Overall strength of sentiment
) 