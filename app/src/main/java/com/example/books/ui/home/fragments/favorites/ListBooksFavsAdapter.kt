package com.example.books.ui.home.fragments.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.books.R
import com.example.books.core.Utilities
import com.example.books.core.ViewHolderGeneral
import com.example.books.databinding.ListBooksBinding
import com.example.books.databinding.ListBooksFavsBinding
import com.example.books.ui.home.fragments.Books.data.BooksInfo
import com.example.books.ui.home.fragments.favorites.BooksFav
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson

class ListBooksFavsAdapter
    (
    private val item: List<BooksFav>, private val lifecycleOwner: LifecycleOwner,
    private val itemClickListener: OnClickListener,
) : RecyclerView.Adapter<ViewHolderGeneral<*>>() {
    companion object {
        val bookSelectFav = ArrayList<BooksFav>()
    }

    interface OnClickListener {
        fun onClick(item: BooksFav, position: Int, cardviewlista: MaterialCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding =
            ListBooksFavsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val itemholder = ViewHolder(itemBinding, parent.context)
        itemBinding.root.setOnClickListener {
            val position =
                itemholder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onClick(item[position], position, itemBinding.root)
        }
        return itemholder
    }

    override fun onBindViewHolder(holder: ViewHolderGeneral<*>, position: Int) {
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fade_in)
        animation.duration = 500
        holder.itemView.startAnimation(animation)
        when (holder) {
            is ViewHolder -> holder.bind(item[position])
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = item.size
    private inner class ViewHolder(val binding: ListBooksFavsBinding, val context: Context) :
        ViewHolderGeneral<BooksFav>(binding.root) {
        override fun bind(item: BooksFav) {
            val autor = item.authors.toString().replace("[", "").replace("]", "")
            val titulo = item.title
            val imageSmall = item.thumbnail
            var isFilled = false
            binding.titleBook.text = titulo
            val httpsUrl = imageSmall?.replace("http://", "https://")
            val imageView = binding.imageBook
            Glide.with(context)
                .load(httpsUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }
    }
}