package com.camihruiz24.cupcake.test

import androidx.navigation.NavController
import kotlin.test.assertEquals

fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    assertEquals(
        expected = expectedRouteName,
        actual = currentBackStackEntry?.destination?.route
    )
}
