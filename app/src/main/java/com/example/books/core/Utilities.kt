package com.example.books.core

import android.app.Activity
import android.content.Context
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.books.R
import com.example.books.ui.home.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class Utilities {

    fun loadFragment(activity: Activity,fragment: Fragment, tag: String) {
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, tag)
            .commit()
    }

     fun showBottomSheetDialog(context: Context, text: String) {
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_description)
        val description = bottomSheetDialog.findViewById<TextView>(R.id.description_book)
        if (description != null) {
            description.text = text
        }
        bottomSheetDialog.show()
    }
}