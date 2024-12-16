package com.example.eats.feedback

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class RealtimeFeedbackMonitor {
    private val db = FirebaseFirestore.getInstance()
    private var listenerRegistration: ListenerRegistration? = null
    private val _realtimeFeedback = MutableStateFlow<List<UserFeedback>>(emptyList())
    
    fun startMonitoring(): Flow<List<UserFeedback>> {
        listenerRegistration = db.collection("feedback")
            .orderBy("timestamp")
            .limitToLast(100)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                
                snapshot?.let { docs ->
                    val feedback = docs.mapNotNull { doc ->
                        doc.toObject(UserFeedback::class.java)
                    }
                    _realtimeFeedback.value = feedback
                    analyzeFeedbackTrends(feedback)
                }
            }
        
        return _realtimeFeedback
    }

    private fun analyzeFeedbackTrends(feedback: List<UserFeedback>) {
        val urgentIssues = feedback.filter { 
            it.rating <= 2 && it.type == FeedbackType.BUG_REPORT 
        }
        if (urgentIssues.isNotEmpty()) {
            notifyUrgentIssues(urgentIssues)
        }
    }

    private fun notifyUrgentIssues(issues: List<UserFeedback>) {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Urgent Feedback")
            .setContentText("${issues.size} urgent issues need attention")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(context).notify(URGENT_NOTIFICATION_ID, notification)
    }

    fun stopMonitoring() {
        listenerRegistration?.remove()
    }
} 