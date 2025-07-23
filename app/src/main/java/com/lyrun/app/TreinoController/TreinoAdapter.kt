package com.lyrun.app.TreinoController

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lyrun.app.databinding.ItemTreinoBinding

class TreinoAdapter(private val exercicios: List<String>) : RecyclerView.Adapter<TreinoAdapter.TreinoViewHolder>() {

    inner class TreinoViewHolder(val binding: ItemTreinoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreinoViewHolder {
        val binding = ItemTreinoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TreinoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TreinoViewHolder, position: Int) {
        holder.binding.tvExercicio.text = exercicios[position]
    }

    override fun getItemCount() = exercicios.size
}
