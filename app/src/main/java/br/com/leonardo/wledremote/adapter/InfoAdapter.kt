package br.com.leonardo.wledremote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.leonardo.wledremote.databinding.RowInfoItemBinding
import br.com.leonardo.wledremote.model.ui.InfoItem

class InfoAdapter(private val items: List<InfoItem>) :
    RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowInfoItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(val binding: RowInfoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(info: InfoItem) {
            binding.infoItemTitle.text = info.title
            binding.infoItemIcon.setImageResource(info.iconId)
            binding.infoItemText.text = info.text
            binding.executePendingBindings()
        }
    }
}