package com.camihruiz24.cupcake

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.camihruiz24.cupcake.data.DataSource
import com.camihruiz24.cupcake.data.OrderModel
import com.camihruiz24.cupcake.ui.screens.CupcakeOrderViewModel
import com.camihruiz24.cupcake.ui.screens.OrderSummaryScreen
import com.camihruiz24.cupcake.ui.screens.SelectOptionScreen
import com.camihruiz24.cupcake.ui.screens.StartOrderScreen
import com.camihruiz24.cupcake.ui.theme.CupcakeScreens
import com.camihruiz24.cupcake.ui.theme.CupcakeTheme
import com.camihruiz24.cupcake.ui.theme.CupcakeTopAppBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CupcakeTheme {
                CupcakeApp()
            }
        }
    }
}

@Composable
fun CupcakeApp(
    cupcakeOrderViewModel: CupcakeOrderViewModel = hiltViewModel(),
    navHostController: NavHostController = rememberNavController()
) {

    /**
     * The Up button should only show if there's a composable on the back stack.
     * If the app has no screens on the back stack — StartOrderScreen is shown —
     * then the Up button should not show. To check this, you need a reference to the back stack.
     */
    val backStackEntry by navHostController.currentBackStackEntryAsState()

    val currentScreen: CupcakeScreens = CupcakeScreens.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreens.Start.name
    )

    // A surface container using the 'background' color from the theme
    Scaffold(
        modifier = Modifier,
        topBar = {
            CupcakeTopAppBar(
                currentScreen = currentScreen,
                canNavigateUp = navHostController.previousBackStackEntry != null,
                navigateUp = navHostController::navigateUp)
        }
    ) {
        val uiState: OrderModel by cupcakeOrderViewModel.uiState.collectAsState()
        AppNavigation(navHostController, cupcakeOrderViewModel, uiState, Modifier.padding(it))
    }
}

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    viewModel: CupcakeOrderViewModel,
    uiState: OrderModel,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navHostController,
        startDestination = CupcakeScreens.Start.name,
        modifier = modifier,
    ) {
        if (navHostController.currentBackStack.value.size == 1)
            navHostController.currentBackStack.value.forEach {
                Log.d("Current backstack", it.destination.toString())
            }
        composable(route = CupcakeScreens.Start.name) { it: NavBackStackEntry ->
            StartOrderScreen(
                quantityOptions = DataSource.quantityOptions,
                onButtonSelected = { it: Int ->
                    viewModel.setCupcakesQuantity(it)
                    navHostController.navigate(CupcakeScreens.Flavor.name)
                    navHostController.currentBackStack.value.forEach {
                        Log.d("Current backstack", it.destination.toString())
                    }
                },
            )
        }
        composable(route = CupcakeScreens.Flavor.name) {
            with(LocalContext.current) {
                SelectOptionScreen(
                    subtotal = uiState.price,
                    options = DataSource.flavors.map { id: Int ->
                        resources.getString(id)
                    },
                    onSelectionChanged = { viewModel.setFlavor(it) },
                    onNextButtonClicked = {
                        navHostController.navigate(CupcakeScreens.Pickup.name)
                        navHostController.currentBackStack.value.forEach {
                            Log.d("Current backstack", it.destination.toString())
                        }
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navHostController)
                    }
                )
            }
        }
        composable(route = CupcakeScreens.Pickup.name) {
            SelectOptionScreen(
                subtotal = uiState.price,
                options = uiState.pickupOptions,
                onSelectionChanged = { viewModel.setDate(it) },
                onNextButtonClicked = {
                    navHostController.navigate(CupcakeScreens.Summary.name)
                    navHostController.currentBackStack.value.forEach {
                        Log.d("Current backstack", it.destination.toString())
                    }
                },
                onCancelButtonClicked = {
                    cancelOrderAndNavigateToStart(viewModel, navHostController)
                }
            )
        }
        composable(route = CupcakeScreens.Summary.name) {
            val context = LocalContext.current
            OrderSummaryScreen(
                orderUiState = uiState,
                onShareButtonClicked = { subject, summary ->
                    sendOrder(context, subject, summary)
                },
                onCancelButtonClicked = {
                    cancelOrderAndNavigateToStart(viewModel, navHostController)
                }
            )
        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: CupcakeOrderViewModel,
    navHostController: NavHostController
) {
    viewModel.resetOrder()
    navHostController.popBackStack(CupcakeScreens.Start.name, false)
    navHostController.currentBackStack.value.forEach {
        Log.d("Current backstack", it.destination.toString())
    }
}

private fun sendOrder(context: Context, subject: String, summary: String){
    Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }.also { it: Intent ->
        context.startActivity(
            Intent.createChooser(it, context.getString(R.string.new_cupcake_order))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CupcakeAppPreview() {
    CupcakeTheme {
        CupcakeApp()
    }
}