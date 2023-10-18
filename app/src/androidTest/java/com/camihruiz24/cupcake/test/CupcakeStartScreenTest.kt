package com.camihruiz24.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.camihruiz24.cupcake.data.DataSource
import com.camihruiz24.cupcake.ui.screens.StartOrderScreen
import org.junit.Rule
import kotlin.test.Test

class CupcakeStartScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * When quantity options are provided to StartOrderScreen, the options are displayed on the
     * screen.
     */
    @Test
    fun verifyButtonsAreDisplayed() {
        composeTestRule.setContent {
            StartOrderScreen(
                quantityOptions = DataSource.quantityOptions,
                onButtonSelected = {}
            )
        }

        // Then all the options are displayed on the screen.
        DataSource.quantityOptions.forEach {
            composeTestRule.onNodeWithStringId(it.first).assertIsDisplayed()
        }
    }

}
