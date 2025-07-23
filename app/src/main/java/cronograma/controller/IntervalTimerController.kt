package com.lyrun.cronograma.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.CountDownTimer

class IntervalTimerViewModel : ViewModel() {

    private val _tempoRestante = MutableLiveData<Long>()
    val tempoRestante: LiveData<Long> = _tempoRestante

    private val _fase = MutableLiveData<String>()
    val fase: LiveData<String> = _fase

    private val _finalizado = MutableLiveData<Boolean>()
    val finalizado: LiveData<Boolean> = _finalizado

    private var tirosRestantes = 0
    private var tempoTiroMillis = 0L
    private var tempoDescansoMillis = 0L

    private var timer: CountDownTimer? = null

    fun iniciarTreino(
        tiros: Int,
        tempoTiroSeg: Long,
        tempoDescansoSeg: Long
    ) {
        tirosRestantes = tiros
        tempoTiroMillis = tempoTiroSeg * 1000
        tempoDescansoMillis = tempoDescansoSeg * 1000
        _finalizado.value = false
        iniciarFaseTiro()
    }

    private fun iniciarFaseTiro() {
        _fase.value = "Corrida"
        timer?.cancel()
        timer = object : CountDownTimer(tempoTiroMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _tempoRestante.value = millisUntilFinished / 1000
            }

            override fun onFinish() {
                iniciarFaseDescanso()
            }
        }.start()
    }

    private fun iniciarFaseDescanso() {
        _fase.value = "Descanso"
        timer?.cancel()
        timer = object : CountDownTimer(tempoDescansoMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _tempoRestante.value = millisUntilFinished / 1000
            }

            override fun onFinish() {
                tirosRestantes--
                if (tirosRestantes > 0) {
                    iniciarFaseTiro()
                } else {
                    _fase.value = "Finalizado"
                    _tempoRestante.value = 0
                    _finalizado.value = true
                }
            }
        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}
