package com.example.books.ui.home.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.books.R
import com.example.books.core.Utilities
import com.example.books.databinding.ActivityMainBinding
import com.example.books.ui.home.fragments.Books.BooksFragment
import com.example.books.ui.home.fragments.favorites.FavFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        buttons()
    }
    private fun initView() {
        Utilities().loadFragment(this, BooksFragment(), "Books")
    }
    private fun buttons() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    Utilities().loadFragment(this, BooksFragment(), "Books")
                    true
                }

                R.id.fav -> {
                    Utilities().loadFragment(this, FavFragment(), "Fav")
                    true
                }

                else -> false
            }
        }
    }
}