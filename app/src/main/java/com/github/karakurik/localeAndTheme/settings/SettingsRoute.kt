package com.github.karakurik.localeAndTheme.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.github.karakurik.localeAndTheme.MainActivity
import com.github.karakurik.localeAndTheme.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsRoute(
    component: Settings,
) {
    val activity = LocalContext.current as MainActivity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            IconButton(onClick = component::navigateUp) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                )
            }
            Text(
                text = stringResource(id = R.string.greeting),
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        val localeOptions = mapOf(
            R.string.en to "en",
            R.string.fr to "fr",
            R.string.hi to "hi",
            R.string.ja to "ja"
        ).mapKeys { stringResource(it.key) }

        // boilerplate: https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#ExposedDropdownMenuBox(kotlin.Boolean,kotlin.Function1,androidx.compose.ui.Modifier,kotlin.Function1)
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                readOnly = true,
                value = stringResource(R.string.language),
                onValueChange = { },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                localeOptions.keys.forEach { selectionLocale ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            // set app locale given the user's selected locale
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(
                                    localeOptions[selectionLocale]
                                )
                            )
                            activity.animateOpenYourSelf()
                        },
                        content = { Text(selectionLocale) }
                    )
                }
            }
        }

        var expandedTheme by remember { mutableStateOf(false) }
        val themeModes = mapOf(
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM to R.string.system,
            AppCompatDelegate.MODE_NIGHT_NO to R.string.light,
            AppCompatDelegate.MODE_NIGHT_YES to R.string.night,
        ).mapValues { stringResource(it.value) }

        ExposedDropdownMenuBox(
            expanded = expandedTheme,
            onExpandedChange = {
                expandedTheme = !expandedTheme
            }
        ) {
            TextField(
                readOnly = true,
                value = themeModes[AppCompatDelegate.getDefaultNightMode()]
                    ?: stringResource(R.string.system),
                onValueChange = { },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expandedTheme
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expandedTheme,
                onDismissRequest = {
                    expandedTheme = false
                }
            ) {
                themeModes.keys.forEach { selectionTheme ->
                    DropdownMenuItem(
                        onClick = {
                            expandedTheme = false
                            AppCompatDelegate.setDefaultNightMode(
                                selectionTheme
                            )
                            activity.animateOpenYourSelf()
                        },
                        content = { Text(themeModes[selectionTheme].orEmpty()) }
                    )
                }
            }
        }
    }
}
