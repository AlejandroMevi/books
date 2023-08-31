package com.example.books.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.books.core.ApiResponceStatus
import com.example.books.databinding.FragmentBooksBinding
import com.example.books.ui.home.vm.MainViewModel

class BooksFragment : Fragment() {

    private lateinit var binding: FragmentBooksBinding
    private val bookMainViewModel: MainViewModel by viewModels()
    private lateinit var listaBooks: ArrayList<BooksInfo>
    private var startIndex: Int = 0
    private var maxResults: Int = 10
    private var pages: Int = 1
    private var querySave: String = "a"


    companion object {
        lateinit var listArrayResponse: ArrayList<BooksInfo>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBooksBinding.inflate(inflater, container, false)
        callService(querySave,startIndex, maxResults)
        initiView()
        buttons()
        searchBook()
        return binding.root
    }

    private fun initiView() {
        binding.leftArrow.isVisible = false
        binding.numPage.text = pages.toString()
        startIndex = maxResults
        maxResults += 10
    }

    private fun buttons() {
        binding.rightArrow.setOnClickListener {
            startIndex += 10;maxResults += 10
            pages += 1
            binding.numPage.text = pages.toString()
            binding.leftArrow.isVisible = startIndex >= 10
            callService(querySave,startIndex, maxResults)
        }
        binding.leftArrow.setOnClickListener {
            startIndex -= 10;maxResults -= 10
            pages -= 1
            binding.numPage.text = pages.toString()
            binding.leftArrow.isVisible = startIndex > 10
            callService(querySave,startIndex, maxResults)
        }
    }

    private fun searchBook() {
        binding.etFilter.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrEmpty()) {
                querySave = text.toString()
                callService(querySave, startIndex, maxResults)
            } else {
                querySave = "a"
                callService(querySave, startIndex, maxResults)
            }
        }
    }

    private fun callService(query: String, startIndex: Int, maxResults: Int) {
        bookMainViewModel.data.value = null
        bookMainViewModel.getBooks(query, startIndex, maxResults)
        statusObserve()
        bookMainViewModel.data.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                println(response)
                response.items?.let { println(it.size) }
                val list = java.util.ArrayList<BooksInfo>()
                for (i in response.items?.indices!!) {
                    val dataModel = BooksInfo()
                    dataModel.id = response.items[i].id
                    dataModel.title = response.items[i].volumeInfo?.title
                    dataModel.authors = response.items[i].volumeInfo?.authors
                    dataModel.description = response.items[i].volumeInfo?.description
                    dataModel.smallThumbnail =
                        response.items[i].volumeInfo?.imageLinks?.smallThumbnail
                    dataModel.thumbnail = response.items[i].volumeInfo?.imageLinks?.thumbnail
                    dataModel.canonicalVolumeLink =
                        response.items[i].volumeInfo?.canonicalVolumeLink
                    dataModel.infoLink = response.items[i].volumeInfo?.infoLink
                    dataModel.previewLink = response.items[i].volumeInfo?.previewLink
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
        bookMainViewModel.status.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        binding.progressIndicator.isVisible = true
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
        bookMainViewModel.status.removeObservers(viewLifecycleOwner)
        bookMainViewModel.status.value = null
        binding.progressIndicator.isVisible = false
    }

}