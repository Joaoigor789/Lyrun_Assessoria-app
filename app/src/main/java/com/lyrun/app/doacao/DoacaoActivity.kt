package com.lyrun.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DoacaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doacao)

        val btnDoar = findViewById<Button>(R.id.btnDoar)

        btnDoar.setOnClickListener {
            val uri = Uri.parse("https://picpay.me/joao.igor77/1.0")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


    }
}
