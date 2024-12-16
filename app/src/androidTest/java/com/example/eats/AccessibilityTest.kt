package com.example.eats

import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccessibilityTest {
    @Before
    fun setUp() {
        AccessibilityChecks.enable()
    }

    @Test
    fun testMainScreenAccessibility() {
        // Test navigation elements
        onView(withId(R.id.bottom_navigation))
            .check(matches(isDisplayed()))
            .check(matches(hasContentDescription()))

        // Test restaurant cards
        onView(withId(R.id.card_stack_view))
            .check(matches(isDisplayed()))
            .check(matches(isFocusable()))
    }
} 