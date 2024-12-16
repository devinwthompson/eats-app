package com.example.eats

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GroupFlowTest {
    @Test
    fun testGroupCreation() {
        // Navigate to groups
        onView(withId(R.id.nav_groups))
            .perform(click())

        // Create new group
        onView(withId(R.id.create_group_button))
            .perform(click())

        // Fill group details
        onView(withId(R.id.group_name_input))
            .perform(typeText("Lunch Squad"))
        onView(withId(R.id.invite_friends_button))
            .perform(click())

        // Verify group created
        onView(withText("Lunch Squad"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testGroupVoting() {
        // Test restaurant voting in group
        onView(withId(R.id.restaurant_card))
            .perform(swipeRight())
        
        // Verify match notification
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(containsString("Match!"))))
    }
} 