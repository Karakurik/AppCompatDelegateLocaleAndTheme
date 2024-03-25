package com.github.karakurik.localeAndTheme

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.github.karakurik.localeAndTheme.theme.AppCompatDelegateLocaleAndThemeTheme

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppCompatDelegateLocaleAndThemeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize(Alignment.TopCenter)
                            .padding(top = 48.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.greeting),
                            fontSize = 30.sp,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )
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
                                            with(this@MainActivity) {
                                                startActivity(
                                                    Intent(
                                                        this,
                                                        MainActivity::class.java
                                                    ),
                                                    ActivityOptions.makeCustomAnimation(
                                                        this@MainActivity,
                                                        android.R.anim.fade_in,
                                                        android.R.anim.fade_out,
                                                    ).toBundle()
                                                )
                                                finish()
                                            }
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
                                            with(this@MainActivity) {
                                                startActivity(
                                                    Intent(
                                                        this,
                                                        MainActivity::class.java
                                                    ),
                                                    ActivityOptions.makeCustomAnimation(
                                                        this@MainActivity,
                                                        android.R.anim.fade_in,
                                                        android.R.anim.fade_out,
                                                    ).toBundle()
                                                )
                                                finish()
                                            }
                                        },
                                        content = { Text(themeModes[selectionTheme].orEmpty()) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
