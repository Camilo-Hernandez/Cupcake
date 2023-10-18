package com.camihruiz24.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.camihruiz24.cupcake.R
import com.camihruiz24.cupcake.data.OrderModel
import com.camihruiz24.cupcake.ui.screens.OrderSummaryScreen
import org.junit.Rule
import kotlin.test.Test

class CupcakeSummaryScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val uiState = OrderModel(
        quantity = 1,
        price = "1200",
        flavor = "Chocolate",
        date = "Thu Oct 18",
        pickupOptions = listOf(buildString {
            append("Hello, ")
            append("world!")
        }),
    )

    @Test
    fun verifyButtonsAreDisplayed() {
        composeTestRule.setContent {
            OrderSummaryScreen(
                orderUiState = uiState,
                onCancelButtonClicked = { },
                onShareButtonClicked = { _, _ -> },
            )
        }

        // Verify the Quantity label is shown
        composeTestRule.onNodeWithStringId(R.string.quantity, ignoreCase = true).assertIsDisplayed()
        // Verify the quantity of cupcakes is shown correctly with plurals
        composeTestRule.onNodeWithText(
            composeTestRule.activity.resources.getQuantityString(
                R.plurals.cupcakes,
                uiState.quantity,
                uiState.quantity
            )
        ).assertIsDisplayed()
        // Verify the Flavor label is shown
        composeTestRule.onNodeWithStringId(R.string.flavor, ignoreCase = true).assertIsDisplayed()
        // Verify the flavor is shown
        composeTestRule.onNodeWithText(uiState.flavor).assertIsDisplayed()
        // Verify the Pickup Date label is shown
        composeTestRule.onNodeWithStringId(R.string.pickup_date, ignoreCase = true)
            .assertIsDisplayed()
        // Verify the pickup date is shown
        composeTestRule.onNodeWithText(uiState.date).assertIsDisplayed()
        // Verify the other pickup dates are not shown
        composeTestRule.onNodeWithText(uiState.pickupOptions.random()).assertDoesNotExist()
        // Verify the price label is shown with the correct format
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.subtotal_price, uiState.price)
        ).assertIsDisplayed()
        // Verify Send and Cancel buttons are shown
        composeTestRule.onNodeWithStringId(R.string.send).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.cancel).assertIsDisplayed()
    }
}
