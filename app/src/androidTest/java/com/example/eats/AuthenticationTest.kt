package com.example.eats

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthenticationTest {
    @Test
    fun testLoginFlow() {
        onView(withId(R.id.email_input))
            .perform(typeText("test@example.com"))
        onView(withId(R.id.password_input))
            .perform(typeText("password123"))
        onView(withId(R.id.login_button))
            .perform(click())
    }
} 