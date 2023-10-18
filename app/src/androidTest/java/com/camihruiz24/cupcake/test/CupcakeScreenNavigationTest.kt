package com.camihruiz24.cupcake.test

import java.text.SimpleDateFormat
import java.util.Calendar
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.camihruiz24.cupcake.CupcakeApp
import com.camihruiz24.cupcake.R
import com.camihruiz24.cupcake.ui.theme.CupcakeScreens
import org.junit.Rule
import java.util.Locale
import kotlin.test.BeforeTest
import kotlin.test.Test

class CupcakeScreenNavigationTest {

    @get:Rule
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity> =
        createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @BeforeTest
    fun setupTestNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            CupcakeApp(navHostController = navController)
        }
    }

    @Test
    fun verifyNavHost_startDestinationIsStartScreen() {
        navController.assertCurrentRouteName(CupcakeScreens.Start.name)
    }

    @Test
    fun verifyNavHost_upButtonIsNotPresentInStartScreen() {
        val backButtonText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backButtonText).assertDoesNotExist()
    }

    @Test
    fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavorsScreen() {
        composeTestRule.onNodeWithStringId(R.string.one_cupcake).assertExists()
        composeTestRule.onNodeWithStringId(R.string.one_cupcake).performClick()
        navController.assertCurrentRouteName(CupcakeScreens.Flavor.name)
    }

    @Test
    fun cupcakeNavHost_clickNextOnFlavorsScreen_navigatesToPickupDatesScreen() {
        navigateToPickupDatesScreen()
        navController.assertCurrentRouteName(CupcakeScreens.Pickup.name)
    }

    @Test
    fun cupcakeNavHost_clickNextOnPickupDatesScreen_navigatesToSummaryScreen() {
        navigateToSummaryScreen()
        navController.assertCurrentRouteName(CupcakeScreens.Summary.name)
    }

    @Test
    fun cupcakeNavHost_clickBackButtonInFlavorsScreen_navigatesToStartScreen() {
        navigateToFlavorsScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreens.Start.name)
    }

    @Test
    fun cupcakeNavHost_clickBackButtonInPickupDatesScreen_navigatesToFlavorScreen() {
        navigateToPickupDatesScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreens.Flavor.name)
    }

    @Test
    fun cupcakeNavHost_clickBackButtonInSummaryScreen_navigatesToPickupDatesScreen() {
        navigateToSummaryScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreens.Pickup.name)
    }

    @Test
    fun cupcakeNavHost_clickCancelButtonInFlavorsScreen_navigatesToStartScreen() {
        navigateToFlavorsScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreens.Start.name)
    }

    @Test
    fun cupcakeNavHost_clickCancelButtonInPickupDatesScreen_navigatesToStartScreen() {
        navigateToPickupDatesScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreens.Start.name)
    }

    @Test
    fun cupcakeNavHost_clickCancelButtonInSummaryScreen_navigatesToStartScreen() {
        navigateToSummaryScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreens.Start.name)
    }

    /**
     * The Cupcake app has a mostly linear navigation flow. Short of clicking the Cancel button,
     * you can only navigate through the app in one direction. Therefore, as you test screens that
     * are deeper within the app, you can find yourself repeating code to navigate to the areas you
     * want to test. This situation merits the use of more helper methods so that you only have to
     * write that code once.
     *
     * Now that you tested navigation to the Flavor screen, create a method that navigates to the
     * Flavor screen so that you don't have to repeat that code for future tests.
     */
    private fun navigateToFlavorsScreen() {
        composeTestRule.onNodeWithStringId(R.string.one_cupcake)
            .performClick()
    }

    private fun generateTodayAsFormattedDate(): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    private fun navigateToPickupDatesScreen() {
        navigateToFlavorsScreen()
        composeTestRule.onNodeWithStringId(R.string.chocolate)
            .performClick()
        composeTestRule.onNodeWithStringId(R.string.next)
            .performClick()
    }

    private fun navigateToSummaryScreen() {
        navigateToPickupDatesScreen()
        composeTestRule.onNodeWithText(generateTodayAsFormattedDate())
            .performClick()
        composeTestRule.onNodeWithStringId(R.string.next)
            .performClick()
    }

    private fun performNavigateUp() {
        val backButton: String = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backButton).performClick()
    }


}

