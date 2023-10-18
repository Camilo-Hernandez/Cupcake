package com.camihruiz24.cupcake.data

import com.camihruiz24.cupcake.R

object DataSource {
    val flavors: List<Int> = listOf(
        R.string.vanilla,
        R.string.chocolate,
        R.string.red_velvet,
        R.string.salted_caramel,
        R.string.coffee
    )

    val quantityOptions = listOf(
        Pair(R.string.one_cupcake, 1),
        Pair(R.string.six_cupcakes, 6),
        Pair(R.string.twelve_cupcakes, 12)
    )

    /** Price for a single cupcake */
    const val PRICE_PER_CUPCAKE = 2.00

    /** Additional cost for same day pickup of an order */
    const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

}