package com.example.eats.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FeedbackDashboard {
    private val db = FirebaseFirestore.getInstance()
    private val _feedbackStats = MutableLiveData<FeedbackStats>()
    val feedbackStats: LiveData<FeedbackStats> = _feedbackStats

    fun loadFeedbackStats() {
        db.collection("feedback")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(1000)
            .get()
            .addOnSuccessListener { snapshot ->
                val stats = FeedbackStats(
                    totalCount = snapshot.size(),
                    averageRating = snapshot.documents
                        .mapNotNull { it.getLong("rating")?.toDouble() }
                        .average(),
                    typeDistribution = snapshot.documents
                        .groupBy { it.getString("type") }
                        .mapValues { it.value.size },
                    recentTrends = calculateTrends(snapshot.documents)
                )
                _feedbackStats.value = stats
            }
    }

    fun exportFeedbackReport(): String {
        // Generate CSV report
        return buildString {
            appendLine("Date,Type,Rating,Content")
            // Add feedback data rows
        }
    }
}

data class FeedbackStats(
    val totalCount: Int,
    val averageRating: Double,
    val typeDistribution: Map<String?, Int>,
    val recentTrends: Map<String, Double>
) 