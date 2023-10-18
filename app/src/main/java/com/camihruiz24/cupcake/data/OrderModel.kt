package com.camihruiz24.cupcake.data

/**
 * Data class that represents the current UI state in terms of [quantity], [flavor],
 * [pickupOptions], selected pickup [date] and [price]
 */
data class OrderModel(
    /** Selected cupcake quantity (1, 6, 12) */
    val quantity: Int = 0,
    /** Flavor of the cupcakes in the order (such as "Chocolate", "Vanilla", etc..) */
    val flavor: String = "",
    /** Total price for the order */
    val price: String = "",
    /** Selected date for pickup (such as "Jan 1") */
    val date: String = "",
    /** Available pickup dates for the order*/
    val pickupOptions: List<String>,
)
