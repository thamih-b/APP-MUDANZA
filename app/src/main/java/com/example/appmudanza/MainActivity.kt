package com.example.appmudanza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.appmudanza.navigation.AppNavGraph
import com.example.appmudanza.ui.theme.APPMUDANZATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Usa o setContent correto da biblioteca Activity
        setContent {
            APPMUDANZATheme {
                // Chama o grafo de navegação para iniciar na tela de Login
                AppNavGraph()
            }
        }
    }
}
