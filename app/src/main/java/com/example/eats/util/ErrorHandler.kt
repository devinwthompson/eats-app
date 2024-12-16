package com.example.eats.util

import android.content.Context
import com.google.firebase.FirebaseException
import retrofit2.HttpException
import java.net.UnknownHostException

class ErrorHandler(private val context: Context) {
    fun handleError(error: Throwable, retry: () -> Unit) {
        when (error) {
            is FirebaseException -> handleFirebaseError(error, retry)
            is HttpException -> handleNetworkError(error)
            is UnknownHostException -> handleConnectivityError()
            else -> handleGenericError(error)
        }
    }

    private fun handleFirebaseError(error: FirebaseException, retry: () -> Unit) {
        when (error.message) {
            "PERMISSION_DENIED" -> showLoginPrompt()
            "NETWORK_ERROR" -> showRetryDialog(retry)
            else -> showErrorMessage(error.localizedMessage)
        }
    }

    private fun handleNetworkError(error: HttpException) {
        when (error.code()) {
            401 -> showLoginPrompt()
            429 -> showRateLimitError()
            else -> showErrorMessage("Network error: ${error.message()}")
        }
    }

    private fun showRetryDialog(retry: () -> Unit) {
        // Show retry dialog implementation
    }

    private fun showLoginPrompt() {
        // Show login prompt implementation
    }

    private fun showRateLimitError() {
        // Show rate limit error implementation
    }

    private fun showErrorMessage(message: String?) {
        // Show error message implementation
    }
} 