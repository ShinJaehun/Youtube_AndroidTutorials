package com.shinjaehun.imagefilterapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.imagefilterapp.R
import com.shinjaehun.imagefilterapp.data.ImageFilter
import com.shinjaehun.imagefilterapp.databinding.ItemContainerFilterBinding
import com.shinjaehun.imagefilterapp.listeners.ImageFilterListener

class ImageFiltersAdapter(
    private val imageFilters: List<ImageFilter>,
    private val imageFilterListener: ImageFilterListener
) : RecyclerView.Adapter<ImageFiltersAdapter.ImageFilterViewHolder>() {

    private var selectedFilterPosition = 0
    private var previouslySelectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFilterViewHolder {
        val binding = ItemContainerFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageFilterViewHolder(binding)
    }

    override fun getItemCount() = imageFilters.size

    override fun onBindViewHolder(holder: ImageFilterViewHolder, position: Int) {
        with(holder) {
            with(imageFilters[position]) {
                binding.ivFilterPreView.setImageBitmap(filterPreview)
                // 근데 여기 filterPreview는 어째서 갑툭튀?????
                // 아... 아마 with를 사용해서... imageFilter 내의 값을 사용할 수 있는거 아닌가...
                binding.tvFilterName.text = name
                binding.root.setOnClickListener {
                    // imageFilterListener.onFilterSelected(this)

                    if (position != selectedFilterPosition) {
                        // 선택한 필터의 position과 previous position 등록
                        imageFilterListener.onFilterSelected(this)
                        previouslySelectedPosition = selectedFilterPosition
                        selectedFilterPosition = position
                        with(this@ImageFiltersAdapter){
                            notifyItemChanged(previouslySelectedPosition, Unit)
                            notifyItemChanged(selectedFilterPosition, Unit)
                        }
                    }

                }
            }

            binding.tvFilterName.setTextColor(
                // position을 이용해서 필터 이름 색 넣기
                ContextCompat.getColor(
                    binding.tvFilterName.context,
                    if (selectedFilterPosition == position)
                        R.color.primaryDark
                    else
                        R.color.primaryText
                )
            )
        }
    }

    // view binding하면서 adapter에 binding 사용하는거 이제야 제대로 된 거를 보는 듯...
    inner class ImageFilterViewHolder(val binding: ItemContainerFilterBinding) : RecyclerView.ViewHolder(binding.root)
}
