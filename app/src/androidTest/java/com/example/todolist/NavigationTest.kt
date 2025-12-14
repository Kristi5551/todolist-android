package com.example.todolist

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.runtime.Composable
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_simple_navigation() {
        composeTestRule.setContent {
            Column {
                Text("Home Screen")
                Button(onClick = {}) {
                    Text("Go to Details")
                }
            }
        }

        composeTestRule.onNodeWithText("Home Screen").assertExists()
        composeTestRule.onNodeWithText("Go to Details").performClick()
    }
}