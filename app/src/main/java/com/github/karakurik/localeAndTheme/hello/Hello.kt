package com.github.karakurik.localeAndTheme.hello

import com.github.karakurik.localeAndTheme.root.DecomposeComponent

interface Hello : DecomposeComponent {

    fun openSettings()
}

class HelloComponent(
    private val navigateToSettings: () -> Unit,
) : Hello {

    override fun openSettings() = navigateToSettings()
}
