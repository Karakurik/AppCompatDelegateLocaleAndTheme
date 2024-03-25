package com.github.karakurik.localeAndTheme

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.retainedComponent
import com.github.karakurik.localeAndTheme.root.RootNavigation
import com.github.karakurik.localeAndTheme.root.RootNavigationComponent
import com.github.karakurik.localeAndTheme.root.RootRoute
import com.github.karakurik.localeAndTheme.theme.AppCompatDelegateLocaleAndThemeTheme

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val root = retainedComponent {
            RootNavigationComponent(it)
        }

        setContent {
            AppCompatDelegateLocaleAndThemeTheme {
                RootRoute(component = root)
            }
        }
    }

    fun animateOpenYourSelf() {
        startActivity(
            Intent(
                this,
                MainActivity::class.java
            ),
            ActivityOptions.makeCustomAnimation(
                this,
                android.R.anim.fade_in,
                android.R.anim.fade_out,
            ).toBundle()
        )
        finish()
    }
}
