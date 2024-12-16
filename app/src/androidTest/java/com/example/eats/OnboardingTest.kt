package com.example.eats

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingTest {
    @Test
    fun testOnboardingFlow() {
        // Test first page
        onView(withId(R.id.onboarding_pager))
            .check(matches(isDisplayed()))
        onView(withId(R.id.next_button))
            .perform(click())

        // Test profile setup
        onView(withId(R.id.name_input))
            .perform(typeText("Test User"))
        onView(withId(R.id.food_preferences_spinner))
            .perform(click())
        onView(withText("Italian"))
            .perform(click())
        onView(withId(R.id.finish_button))
            .perform(click())
    }
} 