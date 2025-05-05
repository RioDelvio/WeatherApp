package com.rio.weatherapp.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.rio.weatherapp.presentation.details.DetailsContent
import com.rio.weatherapp.presentation.favourite.FavouriteContent
import com.rio.weatherapp.presentation.search.SearchContent
import com.rio.weatherapp.presentation.ui.theme.WeatherAppTheme

@Composable
fun RootContent(component: RootComponent) {

    WeatherAppTheme {
        Children(
            stack = component.stack
        ) {
            when (val instance = it.instance) {

                is RootComponent.Child.Details -> {
                    DetailsContent(instance.component)
                }

                is RootComponent.Child.Favourite -> {
                    FavouriteContent(instance.component)
                }

                is RootComponent.Child.Search -> {
                    SearchContent(instance.component)
                }
            }

        }
    }
}