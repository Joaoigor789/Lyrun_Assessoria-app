package com.lyrun.app


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.lyrun.app.LoginActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicia LoginActivity imediatamente
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()

        // Se quiser usar Compose na MainActivity, pode usar setContent assim:
        // setContent {
        //     GreetingPreview()
        // }
    }
}

// Função composable para mostrar texto simples
@Composable
fun Greeting() {
    Text(text = "Olá, Preview!")
}

// Função preview para Android Studio
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting()
}
