package com.rio.weatherapp.presentation.favourite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rio.weatherapp.R
import com.rio.weatherapp.presentation.extensions.toTempFormattedString
import com.rio.weatherapp.presentation.ui.theme.CardGradients
import com.rio.weatherapp.presentation.ui.theme.Gradient
import com.rio.weatherapp.presentation.ui.theme.Orange

@Composable
fun FavouriteContent(component: FavoriteComponent) {

    val state by component.model.collectAsState()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            SearchCard(onClick = { component.clickSearch() })
        }
        itemsIndexed(
            items = state.cityItems,
            key = { _, item -> item.city.id }
        ) { index, item ->
            CityCardItem(
                cityItem = item,
                index = index,
                onClick = { component.clickOnCity(item.city) }
            )
        }
        item {
            AddFavouriteCard(onClick = { component.clickAddToFavourite() })
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CityCardItem(
    cityItem: FavouriteStore.State.CityItem,
    index: Int,
    onClick: () -> Unit
) {

    val gradient = getGradientByIndex(index)

    Card(
        modifier = Modifier
            .fillMaxSize()
            .shadow(
                elevation = 16.dp,
                shape = MaterialTheme.shapes.extraLarge,
                spotColor = gradient.shadowColor
            ),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Box(
            modifier = Modifier
                .background(gradient.primaryGradient)
                .fillMaxSize()
                .sizeIn(minHeight = 200.dp)
                .drawBehind {
                    drawCircle(
                        brush = gradient.secondaryGradient,
                        center = Offset(
                            center.x - size.width / 9,
                            center.y + size.height / 2
                        )
                    )
                }
                .clickable { onClick() }
                .padding(20.dp)
        ) {
            when (val weatherState = cityItem.weatherState) {
                FavouriteStore.State.WeatherState.Error -> {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(58.dp),
                        imageVector = Icons.Default.Cancel,
                        contentDescription = null,
                        tint = Color.Red.copy(alpha = 0.9f)
                    )
                }

                FavouriteStore.State.WeatherState.Initial -> {}
                is FavouriteStore.State.WeatherState.Loaded -> {
                    GlideImage(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(50.dp),
                        model = weatherState.imageUrl,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(bottom = 24.dp),
                        text = weatherState.tempC.toTempFormattedString(),
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 48.sp)
                    )
                }

                FavouriteStore.State.WeatherState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
            Text(
                modifier = Modifier.align(Alignment.BottomStart),
                text = cityItem.city.name,
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun SearchCard(
    onClick: () -> Unit
) {

    val gradient = CardGradients.gradients[3]

    Card(
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient.primaryGradient)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.search),
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Composable
private fun AddFavouriteCard(
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.extraLarge,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Column(
            modifier = Modifier
                .sizeIn(minHeight = 200.dp)
                .fillMaxWidth()
                .padding(24.dp)
                .clickable { onClick() }
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .size(48.dp),
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = Orange
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.add_to_favourite_button),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

private fun getGradientByIndex(index: Int): Gradient {
    val gradients = CardGradients.gradients
    return gradients[index % gradients.size]
}