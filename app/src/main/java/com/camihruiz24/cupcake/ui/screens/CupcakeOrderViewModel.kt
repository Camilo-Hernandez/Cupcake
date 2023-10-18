package com.camihruiz24.cupcake.ui.screens

import android.icu.text.NumberFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camihruiz24.cupcake.data.DataSource
import com.camihruiz24.cupcake.data.OrderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

/**
 * [CupcakeOrderViewModel] holds information about a cupcake order in terms of quantity, flavor, and
 * pickup date. It also knows how to calculate the total price based on these order details.
 */
@HiltViewModel
class CupcakeOrderViewModel @Inject constructor() : ViewModel() {
    private val _uiState by lazy {
        MutableStateFlow(
            OrderModel(pickupOptions = generatePickupDateOptions())
        )
    }

    val uiState = _uiState.asStateFlow()

    /**
     * Assigns the [quantity] value to the [_uiState] attribute
     */
    fun setCupcakesQuantity(quantity: Int) {
        _uiState.update {
            it.copy(
                quantity = quantity, price = calculatePrice(quantity)
            )
        }
    }

    /**
     * Set the [desiredFlavor] of cupcakes for this order's state.
     * Only 1 flavor can be selected for the whole order.
     */
    fun setFlavor(desiredFlavor: String) {
        _uiState.update {
            it.copy(
                flavor = desiredFlavor
            )
        }
    }

    /**
     * Set the [pickupDate] of the order for this order's state.
     * Only one pickup date can be selected for the whole order
     */
    fun setDate(pickupDate: String) {
        _uiState.update {
            it.copy(
                date = pickupDate, price = calculatePrice(pickupDate = pickupDate)
            )
        }
    }

    /**
     * Set the attributes of the state to their initial value
     */
    fun resetOrder() {
        _uiState.update {
            it.copy(
                quantity = 0,
                flavor = "",
                price = "",
                date = "",
                pickupOptions = generatePickupDateOptions()
            )
        }
    }

    /**
     * Returns the calculated price based on the order details.
     */
    private fun calculatePrice(
        quantity: Int = _uiState.value.quantity,
        pickupDate: String = _uiState.value.date,
    ): String {
        var price = quantity * DataSource.PRICE_PER_CUPCAKE
        // If the user selected the first option (today) for pickup, add the surcharge
        if (pickupDate == generatePickupDateOptions()[0]) {
            price += DataSource.PRICE_FOR_SAME_DAY_PICKUP
        }

        return NumberFormat.getCurrencyInstance().format(price)
    }

    /**
     * Returns a list of date options starting with the current date and the following 3 dates.
     */
    private fun generatePickupDateOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        viewModelScope.launch {
            val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
            val calendar = Calendar.getInstance()
            // add current date and the following 3 dates.
            repeat(4) {
                dateOptions.add(formatter.format(calendar.time))
                calendar.add(Calendar.DATE, 1)
            }
        }
        return dateOptions
    }

}
