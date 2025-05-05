package com.rio.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.rio.weatherapp.WeatherApplication
import com.rio.weatherapp.presentation.root.DefaultRootComponent
import com.rio.weatherapp.presentation.root.RootComponent
import com.rio.weatherapp.presentation.root.RootContent
import com.rio.weatherapp.presentation.ui.theme.WeatherAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as WeatherApplication).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)
        val component = rootComponentFactory.create(defaultComponentContext())
        setContent {
            RootContent(component)
        }
    }
}
