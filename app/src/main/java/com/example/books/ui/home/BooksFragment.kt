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
    private lateinit var listaBooks: ArrayList<BooksInfo>

    companion object {
        lateinit var listArrayResponse: ArrayList<BooksInfo>
    }

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
                val list = java.util.ArrayList<BooksInfo>()
                for (i in response.items.indices) {
                    val dataModel = BooksInfo()
                    dataModel.id = response.items[i].id
                    dataModel.title = response.items[i].volumeInfo.title
                    dataModel.authors = response.items[i].volumeInfo.authors.toString()
                    dataModel.description = response.items[i].volumeInfo.description
                    dataModel.smallThumbnail =
                        response.items[i].volumeInfo.imageLinks.smallThumbnail
                    dataModel.thumbnail = response.items[i].volumeInfo.imageLinks.thumbnail
                    dataModel.canonicalVolumeLink = response.items[i].volumeInfo.canonicalVolumeLink
                    dataModel.infoLink = response.items[i].volumeInfo.infoLink
                    dataModel.previewLink = response.items[i].volumeInfo.previewLink
                    list.add(dataModel)
                    listArrayResponse = list
                    listaBooks = listArrayResponse
                    setDataKardex(listaBooks)
                }
            }
        }
    }
    private fun setDataKardex(listaUsuarios: ArrayList<BooksInfo>) {
        binding.listBooks.adapter = ListBooksAdapter(listaUsuarios)
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