package com.example.eats.feedback

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class FeedbackCollector {
    private val db = FirebaseFirestore.getInstance()
    val feedbackSubmitted = MutableLiveData<Boolean>()

    fun submitFeedback(feedback: UserFeedback) {
        db.collection("feedback")
            .add(feedback)
            .addOnSuccessListener {
                feedbackSubmitted.value = true
            }
            .addOnFailureListener {
                feedbackSubmitted.value = false
            }
    }
}

data class UserFeedback(
    val userId: String,
    val type: FeedbackType,
    val content: String,
    val rating: Int,
    val timestamp: Long = System.currentTimeMillis()
)

enum class FeedbackType {
    BUG_REPORT,
    FEATURE_REQUEST,
    GENERAL_FEEDBACK,
    RESTAURANT_ISSUE
} 