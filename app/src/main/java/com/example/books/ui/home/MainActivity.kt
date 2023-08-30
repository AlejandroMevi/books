package com.example.books.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.books.R
import com.example.books.core.ApiResponceStatus
import com.example.books.databinding.ActivityMainBinding
import com.example.books.ui.home.vm.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val bookMainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        buttons()
    }

    private fun initView() {
        loadFragment(BooksFragment(), "Books")
    }

    private fun buttons() {
        //binding.callService.setOnClickListener { callService() }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(BooksFragment(), "Books")
                    true
                }

                R.id.fav -> {
                    loadFragment(FavFragment(), "Fav")
                    true
                }

                else -> false
            }
        }
    }

    private fun callService() {
        bookMainViewModel.getBooks("a")
        statusObserve()
        bookMainViewModel.data.observe(this) { response ->
            if (response != null) {
                println(response)
                println(response.items.size)
            }
        }
    }

    private fun statusObserve() {
        bookMainViewModel.status.observe(this) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        Toast.makeText(this, "Cargando", Toast.LENGTH_SHORT).show()
                    }

                    is ApiResponceStatus.Success -> {
                        clearService()
                    }

                    is ApiResponceStatus.Error -> {
                        clearService()
                        val errorCode = status.messageId
                        Toast.makeText(this, errorCode, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun clearService() {
        bookMainViewModel.status.removeObservers(this)
        bookMainViewModel.status.value = null
    }

    private fun loadFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, tag)
            .commit()
    }
}