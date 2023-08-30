package com.example.books.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.books.core.ApiResponceStatus
import com.example.books.databinding.FragmentBooksBinding
import com.example.books.ui.home.vm.MainViewModel

class BooksFragment : Fragment() {

    private lateinit var binding: FragmentBooksBinding
    private val bookMainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callService()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBooksBinding.inflate(inflater, container, false)
        return binding.root
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
                        Toast.makeText(requireActivity(), "Cargando", Toast.LENGTH_SHORT).show()
                    }

                    is ApiResponceStatus.Success -> {
                        clearService()
                    }

                    is ApiResponceStatus.Error -> {
                        clearService()
                        val errorCode = status.messageId
                        Toast.makeText(requireActivity(), errorCode, Toast.LENGTH_SHORT).show()
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