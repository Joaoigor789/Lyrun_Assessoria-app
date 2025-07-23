package com.lyrun.app.classe_principal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.lyrun.app.AgendaActivity
import com.lyrun.app.DoacaoActivity
import com.lyrun.app.R
import com.lyrun.app.TreinoActivity
import com.lyrun.app.databinding.ActivityHomeBinding
import com.lyrun.cronograma.view.IntervalTimerActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private lateinit var btnCronometroIntervalo: Button
    private lateinit var btnLinkedin: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtWelcome.text = "Bem-vindo ao Lyrun!"

        binding.btnAgenda.setOnClickListener {
            startActivity(Intent(this, AgendaActivity::class.java))
        }

        binding.btnTreino.setOnClickListener {
            startActivity(Intent(this, TreinoActivity::class.java))
        }

        btnCronometroIntervalo = findViewById(R.id.btnCronometroIntervalo)
        btnCronometroIntervalo.setOnClickListener {
            val intent = Intent(this, IntervalTimerActivity::class.java)
            startActivity(intent)
        }

        btnLinkedin = findViewById(R.id.btnLinkedin)
        btnLinkedin.setOnClickListener {
            val url = "https://www.linkedin.com/in/joao-igor-25b090250/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        val btnPicpay = findViewById<ImageButton>(R.id.btnPicpay)
        btnPicpay.setOnClickListener {
            val intent = Intent(this, DoacaoActivity::class.java)
            startActivity(intent)
        }

    }
}
