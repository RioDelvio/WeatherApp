package com.rio.weatherapp.presentation.details

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.rio.weatherapp.domain.entity.City
import com.rio.weatherapp.domain.entity.Forecast
import com.rio.weatherapp.domain.use_cases.ChangeCityFavouriteUseCase
import com.rio.weatherapp.domain.use_cases.GetForecastUseCase
import com.rio.weatherapp.domain.use_cases.ObserveFavouriteCitiesUseCase
import com.rio.weatherapp.presentation.details.DetailsStore.Intent
import com.rio.weatherapp.presentation.details.DetailsStore.Label
import com.rio.weatherapp.presentation.details.DetailsStore.State
import kotlinx.coroutines.launch
import javax.inject.Inject

interface DetailsStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data object ClickBack : Intent

        data object ClickChangeFavouriteStatus : Intent
    }

    data class State(
        val city: City,
        val isFavourite: Boolean,
        val forecastState: ForecastState
    ) {

        sealed interface ForecastState {

            data object Initial : ForecastState

            data object Loading : ForecastState

            data object Error : ForecastState

            data class Loaded(val forecast: Forecast) : ForecastState
        }
    }

    sealed interface Label {

        data object ClickBack : Label
    }
}

class DetailsStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val changeCityFavouriteUseCase: ChangeCityFavouriteUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val observeFavouriteCitiesUseCase: ObserveFavouriteCitiesUseCase
) {

    fun create(city: City): DetailsStore =
        object : DetailsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DetailsStore",
            initialState = State(
                city = city,
                isFavourite = false,
                forecastState = State.ForecastState.Initial
            ),
            bootstrapper = BootstrapperImpl(city),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {

        data class ForecastLoaded(val forecast: Forecast) : Action

        data class FavouriteStatusChange(val isFavourite: Boolean) : Action

        data object ForecastIsLoading : Action

        data object ForecastLoadingError : Action
    }

    private sealed interface Msg {

        data class ForecastLoaded(val forecast: Forecast) : Msg

        data class FavouriteStatusChange(val isFavourite: Boolean) : Msg

        data object ForecastIsLoading : Msg

        data object ForecastLoadingError : Msg
    }

    private inner class BootstrapperImpl(private val city: City) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                observeFavouriteCitiesUseCase(city.id).collect {
                    dispatch(Action.FavouriteStatusChange(it))
                }
            }
            scope.launch {
                dispatch(Action.ForecastIsLoading)
                try {
                    val forecast = getForecastUseCase(city.id)
                    dispatch(Action.ForecastLoaded(forecast))
                } catch (e: Exception) {
                    dispatch(Action.ForecastLoadingError)
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }

                Intent.ClickChangeFavouriteStatus -> {
                    scope.launch {
                        val isFavourite = getState().isFavourite
                        val city = getState().city
                        if (isFavourite) {
                            changeCityFavouriteUseCase.removeFromFavourite(city.id)
                        } else {
                            changeCityFavouriteUseCase.addToFavourite(city)
                        }
                    }

                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when(action) {
                is Action.FavouriteStatusChange -> {
                    dispatch(Msg.FavouriteStatusChange(action.isFavourite))
                }
                Action.ForecastIsLoading -> {
                    dispatch(Msg.ForecastIsLoading)
                }
                is Action.ForecastLoaded -> {
                    dispatch(Msg.ForecastLoaded(action.forecast))
                }
                Action.ForecastLoadingError -> {
                    dispatch(Msg.ForecastLoadingError)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when(msg) {
            is Msg.FavouriteStatusChange -> {
                copy(
                    isFavourite = msg.isFavourite
                )
            }
            Msg.ForecastIsLoading -> {
                copy(
                    forecastState = State.ForecastState.Loading
                )
            }
            is Msg.ForecastLoaded -> {
                copy(
                    forecastState = State.ForecastState.Loaded(msg.forecast)
                )
            }
            Msg.ForecastLoadingError -> {
                copy(
                    forecastState = State.ForecastState.Error
                )
            }
        }
    }
}
