package com.camihruiz24.cupcake.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.camihruiz24.cupcake.R
import com.camihruiz24.cupcake.data.OrderModel
import com.camihruiz24.cupcake.ui.components.FormattedPriceLabel

/**
 * This composable expects [orderUiState] that represents the order state, [onCancelButtonClicked]
 * lambda that triggers canceling the order and passes the final order to [onShareButtonClicked]
 * lambda
 */
@Composable
fun OrderSummaryScreen(
    orderUiState: OrderModel,
    onCancelButtonClicked: () -> Unit,
    onShareButtonClicked: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources

    /**
     * Función que utiliza el siguiente recurso de `strings.xml` para mostrar la formar plural o
     * singular de la cantidad de Cupcakes
     * ```xml
     * <plurals name="cupcakes">
     *    <item quantity="one">%d cupcake</item>
     *    <item quantity="other">%d cupcakes</item>
     * </plurals>
     * ```
     */
    val numberOfCupcakes = resources.getQuantityString(
        R.plurals.cupcakes,
        orderUiState.quantity,
        orderUiState.quantity,
    )

    // Load and format a string resource with the parameters.
    val orderSummary = stringResource(
        R.string.order_details,
        numberOfCupcakes,
        orderUiState.flavor,
        orderUiState.date,
        orderUiState.quantity
    )

    val newOrder: String = stringResource(R.string.new_cupcake_order)

    // Create a list of order summary to display
    val items = listOf(
        // Summary line 1: display selected quantity
        Pair(stringResource(R.string.quantity), numberOfCupcakes),
        // Summary line 2: display selected flavor
        Pair(stringResource(R.string.flavor), orderUiState.flavor),
        // Summary line 3: display selected pickup date
        Pair(stringResource(R.string.pickup_date), orderUiState.date)
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            items.forEach { item ->
                Text(item.first.uppercase())
                Text(text = item.second, fontWeight = FontWeight.Bold)
                Divider(thickness = dimensionResource(R.dimen.thickness_divider))
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            FormattedPriceLabel(
                subtotal = orderUiState.price,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f, false)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onShareButtonClicked(
                        newOrder,
                        orderSummary,
                    ) }
                ) {
                    Text(stringResource(R.string.send))
                }
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onCancelButtonClicked
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrderSummaryPreview() {
    OrderSummaryScreen(
        orderUiState = OrderModel(123, "Flavor Test", "$400.00", "Fri Oct 24", emptyList()),
        onCancelButtonClicked = {},
        onShareButtonClicked = { _, _ -> },
        modifier = Modifier.fillMaxHeight()
    )
}

