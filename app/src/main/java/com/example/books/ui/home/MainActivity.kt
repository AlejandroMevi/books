package com.example.books.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.books.R
import com.example.books.core.ApiResponceStatus
import com.example.books.core.Utilities
import com.example.books.databinding.ActivityMainBinding
import com.example.books.ui.home.vm.MainViewModel

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
        Utilities().loadFragment(this,BooksFragment(), "Books")
    }
    private fun buttons() {
        //binding.callService.setOnClickListener { callService() }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    Utilities().loadFragment(this,BooksFragment(), "Books")
                    true
                }

                R.id.fav -> {
                    Utilities().loadFragment(this,FavFragment(), "Fav")
                    true
                }

                else -> false
            }
        }
    }
    private fun loadFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, tag)
            .commit()
    }
}