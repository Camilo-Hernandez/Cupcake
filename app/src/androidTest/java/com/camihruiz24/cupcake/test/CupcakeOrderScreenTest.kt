package com.camihruiz24.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.camihruiz24.cupcake.R
import com.camihruiz24.cupcake.ui.screens.SelectOptionScreen
import org.junit.Rule
import kotlin.test.Test

class CupcakeOrderScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectOptionsScreen_verifyContent() {
        // Given list of options
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        // And subtotal
        val subtotal = "$100"

        // When SelectOptionScreen composable is loaded
        composeTestRule.setContent {
            SelectOptionScreen(
                subtotal = subtotal,
                options = flavors,
                onSelectionChanged = {},
                onCancelButtonClicked = {},
                onNextButtonClicked = {})
        }

        // Verify the elements are shown in the composable screen
        flavors.forEach { flavor ->
            composeTestRule.onNodeWithText(flavor).let {
                it.assertExists()
                it.assertIsDisplayed()
            }
        }

        // Verify the price label is shown with the correct format
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.subtotal_price, subtotal)
        ).assertIsDisplayed()

        // Also verify that the next button is enabled only after an option is selected
        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
        composeTestRule.onNodeWithText(flavors.random()).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).assertIsEnabled()

    }
}
