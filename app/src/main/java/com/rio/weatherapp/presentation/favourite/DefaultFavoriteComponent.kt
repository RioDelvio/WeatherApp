package com.rio.weatherapp.presentation.favourite

import com.arkivanov.decompose.ComponentContext

class DefaultFavoriteComponent(
    componentContext: ComponentContext
) : FavoriteComponent, ComponentContext by componentContext