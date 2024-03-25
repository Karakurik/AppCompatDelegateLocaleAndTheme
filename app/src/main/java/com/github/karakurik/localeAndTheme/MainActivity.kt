package com.github.karakurik.localeAndTheme

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.github.karakurik.localeAndTheme.theme.AppCompatDelegateLocaleAndThemeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppCompatDelegateLocaleAndThemeTheme {
                RootRoute()
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
