package com.camihruiz24.cupcake.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camihruiz24.cupcake.R
import com.camihruiz24.cupcake.data.DataSource

@Composable
fun StartOrderScreen(
    quantityOptions: List<Pair<Int, Int>>,
    onButtonSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier,
        Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
    ) {
        Column(
            Modifier.fillMaxWidth(),
            Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            Image(
                painter = painterResource(id = R.drawable.cupcake),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            Text(
                text = stringResource(id = R.string.order_cupcakes),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        }
        Column(
            Modifier.fillMaxWidth(),
            Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            Alignment.CenterHorizontally,
        ) {
            quantityOptions.forEach { nameQuantityPair ->
                SelectQuantityButton(
                    labelResourceId = nameQuantityPair.first,
                    onClick = {
                        onButtonSelected(nameQuantityPair.second)
                    }
                )
            }
        }
    }
}

@Composable
fun SelectQuantityButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp),
    ) {
        Text(stringResource(labelResourceId))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StartOrderScreenPreview() {
    StartOrderScreen(
        quantityOptions = DataSource.quantityOptions,
        onButtonSelected = {},
    )
}