package com.lyrun.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lyrun.app.TreinoController.TreinoAdapter
import com.lyrun.app.TreinoSQL.TreinoDBHelper
import com.lyrun.app.databinding.ActivityTreinoBinding

class TreinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTreinoBinding
    private lateinit var dbHelper: TreinoDBHelper
    private lateinit var adapter: TreinoAdapter
    private var exerciciosList = mutableListOf<String>()
    private var dataSelecionada = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTreinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = TreinoDBHelper(this)

        val dp = binding.datePicker
        dataSelecionada = formatDate(dp.year, dp.month, dp.dayOfMonth)

        configurarRecyclerView()

        carregarTreino(dataSelecionada)

        dp.init(dp.year, dp.month, dp.dayOfMonth) { _, year, month, dayOfMonth ->
            dataSelecionada = formatDate(year, month, dayOfMonth)
            carregarTreino(dataSelecionada)
        }

        binding.btnAddExercicio.setOnClickListener {
            val novoExercicio = binding.etNovoExercicio.text.toString().trim()
            if (novoExercicio.isEmpty()) {
                Toast.makeText(this, "Digite um exercício", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            dbHelper.insertTreino(dataSelecionada, novoExercicio)
            binding.etNovoExercicio.text.clear()
            carregarTreino(dataSelecionada)
            Toast.makeText(this, "Exercício adicionado!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun configurarRecyclerView() {
        adapter = TreinoAdapter(exerciciosList)
        binding.rvTreino.layoutManager = LinearLayoutManager(this)
        binding.rvTreino.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun carregarTreino(data: String) {
        exerciciosList.clear()
        exerciciosList.addAll(dbHelper.getTreinosByDate(data))
        adapter.notifyDataSetChanged()
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val m = month + 1
        val mm = if (m < 10) "0$m" else "$m"
        val dd = if (day < 10) "0$day" else "$day"
        return "$year-$mm-$dd"
    }
}
