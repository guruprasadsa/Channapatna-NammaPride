package com.example.channapatna_namma_pride

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.channapatna_namma_pride.ui.navigation.AppNavigation
import com.example.channapatna_namma_pride.ui.theme.ChannapatnaNammaPrideTheme
import com.example.channapatna_namma_pride.util.LocaleManager

class MainActivity : ComponentActivity() {

    /**
     * Applies the saved locale before the activity's resources are inflated.
     */
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleManager.applyLocale(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ChannapatnaNammaPrideTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
