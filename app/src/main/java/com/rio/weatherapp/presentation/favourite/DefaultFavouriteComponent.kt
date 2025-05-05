package com.rio.weatherapp.presentation.favourite

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.rio.weatherapp.domain.entity.City
import com.rio.weatherapp.presentation.extensions.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultFavouriteComponent @AssistedInject constructor(
    private val storeFactory: FavouriteStoreFactory,
    @Assisted("onClickAddToFavourite") private val onClickAddToFavourite: () -> Unit,
    @Assisted("onClickSearch") private val onClickSearch: () -> Unit,
    @Assisted("onClickCity") private val onClickCity: (City) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : FavoriteComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    FavouriteStore.Label.ClickAddFavourite -> {
                        onClickAddToFavourite()
                    }

                    is FavouriteStore.Label.ClickCityItem -> {
                        onClickCity(it.city)
                    }

                    FavouriteStore.Label.ClickSearch -> {
                        onClickSearch()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<FavouriteStore.State>
        get() = store.stateFlow

    override fun clickSearch() {
        store.accept(FavouriteStore.Intent.ClickSearch)
    }

    override fun clickAddToFavourite() {
        store.accept(FavouriteStore.Intent.ClickAddFavourite)
    }

    override fun clickOnCity(city: City) {
        store.accept(FavouriteStore.Intent.ClickCityItem(city))
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("onClickAddToFavourite") onClickAddToFavourite: () -> Unit,
            @Assisted("onClickSearch") onClickSearch: () -> Unit,
            @Assisted("onClickCity") onClickCity: (City) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultFavouriteComponent
    }
}