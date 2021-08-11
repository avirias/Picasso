package com.avirias.picasso.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avirias.picasso.databinding.ItemPhotoGridBinding
import com.avirias.picasso.domain.model.Photo

class PhotoAdapter(
    private val itemClicked: (Photo) -> Unit
) : ListAdapter<Photo, PhotoAdapter.PhotoViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemBinding =
            ItemPhotoGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position), itemClicked)
    }

    inner class PhotoViewHolder(
        private val itemBinding: ItemPhotoGridBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Photo, onClick: (Photo) -> Unit) {
            itemBinding.item = item
            itemBinding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem.hashCode() == newItem.hashCode()

        }
    }


}