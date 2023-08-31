package com.example.books.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.books.R
import com.example.books.core.ViewHolderGeneral
import com.example.books.databinding.ListBooksBinding
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class ListBooksAdapter
    (
    private val item: List<BooksInfo>, private val lifecycleOwner: LifecycleOwner,
    private val itemClickListener: OnClickListener,
) : RecyclerView.Adapter<ViewHolderGeneral<*>>() {
    companion object {
        val bookSelectFav = ArrayList<BooksFav>()

    }

    interface OnClickListener {
        fun onClick(item: BooksInfo, position: Int, cardviewlista: MaterialCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding =
            ListBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
    private inner class ViewHolder(val binding: ListBooksBinding, val context: Context) :
        ViewHolderGeneral<BooksInfo>(binding.root) {
        override fun bind(item: BooksInfo) {
            val autor = item.authors.toString().replace("[", "").replace("]", "")
            val titulo = item.title
            val imageSmall = item.smallThumbnail
            var isFilled = false
            binding.titleBook.text = titulo
            binding.titleAutor.text = autor
            val httpsUrl = imageSmall?.replace("http://", "https://")
            val imageView = binding.imageBook

            Glide.with(context)
                .load(httpsUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)

            binding.iconButton.setOnClickListener {

                if (isFilled) {
                    binding.iconButton.setIconResource(R.drawable.ic_favorite_border)
                    /*
                    val dataModel = BooksFav()
                    dataModel.title = titulo
                    dataModel.authors = autor
                    dataModel.thumbnail = imageSmall
                    dataModel.link = item.infoLink
                    bookSelectFav.contains(dataModel)
                    bookSelectFav.remove(dataModel)
                    val gson = Gson()
                    val arrayListJson = gson.toJson(dataModel)
                    val sharedPreferences = context.getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("miArrayListKey", arrayListJson)
                    editor.apply()*/
                } else {
                    binding.iconButton.setIconResource(R.drawable.ic_favorite_)
                    if (titulo != null) {
                        val sharedPreferences = context.getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("miArrayListKey", item.smallThumbnail)
                        editor.apply()
                    }
                }
                isFilled = !isFilled

                println("Seleccionados : $bookSelectFav")
            }
        }
    }
}