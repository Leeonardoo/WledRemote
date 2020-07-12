package br.com.leonardo.wledremote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.leonardo.wledremote.databinding.RowInfoItemBinding
import br.com.leonardo.wledremote.model.ui.InfoItem

class InfoAdapter : RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

    private var items: List<InfoItem> = listOf()

    fun updateList(items: List<InfoItem>) {
        val diffCallback = DiffCallback(this.items, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.items = items.toList()
        diffResult.dispatchUpdatesTo(this)
    }

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

    inner class DiffCallback(
        private var oldList: List<InfoItem>, private var newList: List<InfoItem>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }
    }
}