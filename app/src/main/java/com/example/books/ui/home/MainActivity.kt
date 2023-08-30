package com.example.books.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.books.core.ApiResponceStatus
import com.example.books.databinding.ActivityMainBinding
import com.example.books.ui.home.vm.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val bookMainViewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buttons()
    }

    private fun buttons() {
        binding.callService.setOnClickListener { callService() }
    }

    private fun callService(){
        bookMainViewModel.getBooks("a")
        statusObserve()
        bookMainViewModel.data.observe(this){response ->
            if (response != null) {
                println(response)
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
}