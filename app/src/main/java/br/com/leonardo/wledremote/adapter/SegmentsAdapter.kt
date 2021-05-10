package br.com.leonardo.wledremote.adapter

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.leonardo.wledremote.databinding.RowSegmentItemBinding
import br.com.leonardo.wledremote.model.state.Segment


class SegmentsAdapter : RecyclerView.Adapter<SegmentsAdapter.ViewHolder>() {

    private var items: List<Segment> = listOf()

    fun updateList(items: List<Segment>) {
        val diffCallback = DiffCallback(this.items, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.items = items.toList()
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowSegmentItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(val binding: RowSegmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var originalHeight = -1
        private var expandedViewHeigh = -1
        @SuppressLint("SetTextI18n")
        fun bind(segment: Segment) {
            binding.segmentSelectedCheckbox.isChecked = segment.selected == true
            binding.segmentText.text = "Segment: " + segment.id
            binding.startLedsInput.editText?.setText(segment.start.toString())
            binding.endLedsInput.editText?.setText(segment.stop.toString())
            binding.groupingInput.editText?.setText(segment.grp.toString())
            binding.spacingInput.editText?.setText(segment.spc.toString())
            binding.togglePower.isChecked = segment.on == true
            binding.brightnessSlider.value = segment.brightness?.toFloat() ?: 0f
            binding.reverseDirectionCheckbox.isChecked = segment.reverse == true
            binding.mirrorEffectCheckbox.isChecked = segment.mirror == true
            binding.expandedView.isVisible = false

            if (expandedViewHeigh < 0) {
                binding.segmentContainer.doOnPreDraw { it ->
                    originalHeight = it.height
                    binding.expandedView.isVisible = true
                    binding.segmentContainer.doOnPreDraw {
                        expandedViewHeigh = it.height
                        binding.expandedView.isVisible = false
                    }
                }
            }

            binding.segmentContainer.setOnClickListener { animateHeightChanged(it, binding.expandedView, binding.expandedView.isVisible) }
            binding.executePendingBindings()
        }

        // TODO: Handle validation based on user input
        private fun validateInput() {

        }

        private fun animateHeightChanged(view: View, expandable: View, collapse: Boolean) {
            val start = if (collapse) expandedViewHeigh else originalHeight
            val end = if (collapse) originalHeight else expandedViewHeigh
            val valueAnimator = ValueAnimator.ofInt(start, end)
            if (!expandable.isVisible && !collapse) {
                expandable.isVisible = true
            }
            valueAnimator.duration = 300
            valueAnimator.interpolator = AccelerateDecelerateInterpolator()
            valueAnimator.addUpdateListener {
                val value = it.animatedValue as Int
                view.updateLayoutParams<ViewGroup.LayoutParams> {
                    height = value
                }
            }
            valueAnimator.addListener(onEnd = {
                expandable.isVisible = !collapse
            })
            valueAnimator.start()
        }
    }

    inner class DiffCallback(private var oldList: List<Segment>, private var newList: List<Segment>) : DiffUtil.Callback() {

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