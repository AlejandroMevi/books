package com.example.books.core

import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.books.R
import com.example.books.ui.home.MainActivity

class Utilities {

    fun loadFragment(activity: Activity,fragment: Fragment, tag: String) {
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, tag)
            .commit()
    }
}