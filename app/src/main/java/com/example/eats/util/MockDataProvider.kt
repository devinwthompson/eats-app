package com.example.eats.util

object MockDataProvider {
    fun getTestRestaurants() = listOf(
        Restaurant(
            id = "1",
            name = "Test Restaurant",
            cuisine = "Italian",
            rating = 4.5
        )
    )

    fun getTestUser() = User(
        userId = "test_user",
        name = "Test User",
        email = "test@example.com"
    )
} 