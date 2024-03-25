package com.github.karakurik.localeAndTheme.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.github.karakurik.localeAndTheme.hello.HelloRoute
import com.github.karakurik.localeAndTheme.settings.SettingsRoute

@Composable
fun RootNavGraph(
    component: RootNavigation,
) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is RootNavigation.Child.HelloChild -> HelloRoute(component = child.component)
            is RootNavigation.Child.SettingsChild -> SettingsRoute(component = child.component)
        }
    }
}
