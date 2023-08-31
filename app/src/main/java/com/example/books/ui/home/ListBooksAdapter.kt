package com.example.books.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.books.R
import com.example.books.core.ViewHolderGeneral
import com.example.books.databinding.ListBooksBinding
import com.squareup.picasso.Picasso

class ListBooksAdapter
    (private val item: List<BooksInfo>) : RecyclerView.Adapter<ViewHolderGeneral<*>>() {
    companion object {
        var bookSelect = MutableLiveData<List<Long>>()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ListBooksBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding, parent.context)
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
    private inner class ViewHolder(val binding: ListBooksBinding, val context: Context) : ViewHolderGeneral<BooksInfo>(binding.root) {
        override fun bind(item: BooksInfo) {
            val autor = item.authors.toString().replace("[", "").replace("]", "")
            val titulo = item.title
            val imageSmall = item.smallThumbnail
            binding.titleBook.text = titulo
            binding.titleAutor.text = autor
            val httpsUrl = imageSmall?.replace("http://", "https://")
            val imageView = binding.imageBook

            Glide.with(context)
                .load(httpsUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.book)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)

            /*
            binding.cbAutorizar.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    solicitudesSelect.add(item.grupo.toString().toLong())
                } else {
                    if (solicitudesSelect.contains(item.grupo.toString().toLong())) {
                        solicitudesSelect.remove(item.grupo.toString().toLong())
                    }
                }
                listSolicitudSelect.value = solicitudesSelect
            }*/
        }
    }
}