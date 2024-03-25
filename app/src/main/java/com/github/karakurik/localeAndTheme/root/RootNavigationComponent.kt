package com.github.karakurik.localeAndTheme.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.github.karakurik.localeAndTheme.hello.Hello
import com.github.karakurik.localeAndTheme.hello.HelloComponent
import com.github.karakurik.localeAndTheme.settings.Settings
import com.github.karakurik.localeAndTheme.settings.SettingsComponent
import kotlinx.serialization.Serializable

interface RootNavigation : DecomposeComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class HelloChild(val component: Hello) : Child
        data class SettingsChild(val component: Settings) : Child
    }
}

class RootNavigationComponent(
    componentContext: ComponentContext,
) : RootNavigation, ComponentContext by componentContext {

    private val navigation = StackNavigation<ScreenConfig>()

    override val childStack: Value<ChildStack<*, RootNavigation.Child>> = childStack(
        source = navigation,
        handleBackButton = true,
        initialConfiguration = ScreenConfig.Hello,
        serializer = ScreenConfig.serializer(),
        childFactory = ::createChild
    )

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: ScreenConfig,
        componentContext: ComponentContext,
    ): RootNavigation.Child {
        return when (config) {
            ScreenConfig.Hello -> RootNavigation.Child.HelloChild(
                component = HelloComponent(
                    navigateToSettings = { navigation.pushNew(ScreenConfig.Settings) }
                )
            )

            ScreenConfig.Settings -> RootNavigation.Child.SettingsChild(
                component = SettingsComponent(
                    navigateUp = navigation::pop,
                )
            )
        }
    }

    @Serializable
    private sealed interface ScreenConfig {
        @Serializable
        data object Hello : ScreenConfig

        @Serializable
        data object Settings : ScreenConfig
    }
}
