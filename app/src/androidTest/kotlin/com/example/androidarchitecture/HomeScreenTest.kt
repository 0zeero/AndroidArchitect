package com.example.androidarchitecture

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeScreen_displaysNavigateButton() {
        composeTestRule.onNodeWithText("Go to Details (Triggered via Core)").assertIsDisplayed()
    }

    @Test
    fun homeScreen_clickButton_navigatesToDetails() {
        composeTestRule.onNodeWithText("Go to Details (Triggered via Core)").performClick()
        composeTestRule.onNodeWithText("Details Screen").assertIsDisplayed()
    }
}
