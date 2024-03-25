package com.github.karakurik.localeAndTheme.settings

import com.github.karakurik.localeAndTheme.root.DecomposeComponent

interface Settings : DecomposeComponent {

    fun navigateUp()
}

class SettingsComponent(
    private val navigateUp: () -> Unit,
) : Settings {

    override fun navigateUp() = navigateUp.invoke()
}
