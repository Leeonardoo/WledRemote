package br.com.leonardo.wledremote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.leonardo.wledremote.databinding.RowEffectBinding

class EffectsAdapter(
    private val effects: List<String>?, private val clickListener: (String) -> Unit
) : RecyclerView.Adapter<EffectsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowEffectBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = effects?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        effects?.get(position)?.let { holder.bind(it, clickListener) }
    }

    class ViewHolder(val binding: RowEffectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(effect: String, clickListener: (String) -> Unit) {
            binding.effectName.text = effect
            binding.effectMainLayout.setOnClickListener { clickListener(effect) }
            binding.executePendingBindings()
        }
    }
}