package com.accessibilitytraining.android.flow.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.accessibilitytraining.android.databinding.ViewHolderImageBinding
import com.accessibilitytraining.android.extension.toDateContentDescription
import com.accessibilitytraining.android.repository.ListDataResponse

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.ImageViewHolder>() {
    private val imageList = mutableListOf<ListDataResponse.ListImageModel>()

    fun setData(data: List<ListDataResponse.ListImageModel>) {
        imageList.clear()
        imageList.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() = imageList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemBinding = ViewHolderImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBindView(imageList[position])
    }

    class ImageViewHolder(private val itemBinding: ViewHolderImageBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBindView(imageData: ListDataResponse.ListImageModel) {
            itemBinding.tvTitle.text = imageData.title
            itemBinding.tvDate.apply {
                text = imageData.date
                contentDescription = imageData.date?.toDateContentDescription()
            }
        }
    }
}