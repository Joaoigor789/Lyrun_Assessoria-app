package com.lyrun.cronograma.view

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.lyrun.app.R
import com.lyrun.cronograma.controller.IntervalTimerViewModel

class IntervalTimerActivity : AppCompatActivity() {

    private lateinit var edtTiros: EditText
    private lateinit var edtTempoTiro: EditText
    private lateinit var edtTempoDescanso: EditText

    private lateinit var txtFase: TextView
    private lateinit var txtTempo: TextView
    private lateinit var btnIniciar: Button

    private val viewModel: IntervalTimerViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interval_timer)

        edtTiros = findViewById(R.id.edtTiros)
        edtTempoTiro = findViewById(R.id.edtTempoTiro)
        edtTempoDescanso = findViewById(R.id.edtTempoDescanso)

        txtFase = findViewById(R.id.txtFase)
        txtTempo = findViewById(R.id.txtTempo)
        btnIniciar = findViewById(R.id.btnIniciar)

        btnIniciar.setOnClickListener {
            val tirosStr = edtTiros.text.toString()
            val tempoTiroStr = edtTempoTiro.text.toString()
            val tempoDescansoStr = edtTempoDescanso.text.toString()

            if (tirosStr.isBlank() || tempoTiroStr.isBlank() || tempoDescansoStr.isBlank()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tiros = tirosStr.toIntOrNull()
            val tempoTiro = tempoTiroStr.toLongOrNull()
            val tempoDescanso = tempoDescansoStr.toLongOrNull()

            if (tiros == null || tiros <= 0 || tempoTiro == null || tempoTiro <= 0 || tempoDescanso == null || tempoDescanso <= 0) {
                Toast.makeText(this, "Insira valores válidos maiores que zero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.iniciarTreino(tiros, tempoTiro, tempoDescanso)
        }

        viewModel.fase.observe(this) { fase ->
            txtFase.text = fase
            tocarBeep()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrar()
            }
        }

        viewModel.tempoRestante.observe(this) {
            txtTempo.text = "$it s"
        }
    }

    private fun tocarBeep() {
        val toneGen = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 300)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun vibrar() {
        val vib = getSystemService(VIBRATOR_SERVICE) as Vibrator
        vib.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
    }
}
