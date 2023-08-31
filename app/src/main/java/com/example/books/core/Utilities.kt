package com.example.books.core

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.books.R
import com.example.books.ui.home.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class Utilities {

    fun loadFragment(activity: Activity, fragment: Fragment, tag: String) {
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, tag)
            .commit()
    }

    fun showBottomSheetDialog(
        context: Context,
        title: String? = null,
        subtitle: String? = null,
        autor: List<String>? = null,
        description: String? = null,
        imageUrl: String? = null
    ) {
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_description)
        val descriptiontxt = bottomSheetDialog.findViewById<TextView>(R.id.description_book)
        val imageView = bottomSheetDialog.findViewById<ImageView>(R.id.imageBook)
        val titletxt = bottomSheetDialog.findViewById<TextView>(R.id.title_book)
        val subtitletxt = bottomSheetDialog.findViewById<TextView>(R.id.subtitle_book)
        val autortxt = bottomSheetDialog.findViewById<TextView>(R.id.autor_book)
        if (descriptiontxt != null) {
            descriptiontxt.text = description
        }
        if (titletxt != null) {
            titletxt.text = title
        }
        if (subtitletxt != null) {
            subtitletxt.text = subtitle
        }
        if (autortxt != null) {
            autortxt.text = autor.toString().replace("[", "").replace("]", "")
        }
        if (imageView != null) {
            Glide.with(context)
                .load(imageUrl?.replace("http://", "https://"))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }



        bottomSheetDialog.show()
    }
}