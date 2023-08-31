package com.example.books.ui.home.fragments.favorites

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.books.R
import com.example.books.core.Utilities
import com.example.books.databinding.FragmentFavBinding
import com.example.books.ui.home.fragments.Books.BooksFragment
import com.google.android.material.card.MaterialCardView

class FavFragment : Fragment(), ListBooksFavsAdapter.OnClickListener {

    private lateinit var binding: FragmentFavBinding
    private lateinit var listaBooks: ArrayList<BooksFav>

    companion object {
        lateinit var listArrayBooksFav: ArrayList<BooksFav>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavBinding.inflate(inflater, container, false)
        loadSharedPreference()
        initButon()
        return binding.root
    }

    private fun initButon(){
        with(binding){
            topAppBar.setNavigationOnClickListener {
                Utilities().loadFragment(requireActivity(), BooksFragment(), "Books")
            }

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.delete -> {
                        val sharedPreferences =
                            requireContext().getSharedPreferences("libros_seleccionados", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.clear()
                        true
                    }
                    else -> false
                }
            }
        }
    }
    private fun loadSharedPreference() {
        val sharedPreferences = requireActivity().getSharedPreferences("mi_pref", Context.MODE_PRIVATE)
        val librosSeleccionadosJson = sharedPreferences.getString("libros_seleccionados", "")
        val librosSeleccionados = librosSeleccionadosJson?.let { Utilities().convertirDesdeJson(it) }
        val list = ArrayList<BooksFav>()
        if (librosSeleccionados != null) {
            for (i in librosSeleccionados.indices) {
                val dataModel = BooksFav()
                dataModel.title = librosSeleccionados[i].title
                dataModel.authors = librosSeleccionados[i].authors
                dataModel.thumbnail = librosSeleccionados[i].thumbnail
                dataModel.link = librosSeleccionados[i].link
                list.add(dataModel)
                listArrayBooksFav = list
                listaBooks = list
                setDataKardex(listaBooks)
            }
        }
    }
    private fun setDataKardex(listaUsuarios: ArrayList<BooksFav>) {
        binding.listBooks.adapter = ListBooksFavsAdapter(listaUsuarios, viewLifecycleOwner, this)
    }

    override fun onClick(item: BooksFav, position: Int, cardviewlista: MaterialCardView) {
        Utilities().showBottomSheetDialog(
            requireActivity(),
            requireContext(),
            item.title,
            item.authors,
            null,
            null,
            item.thumbnail,
            item.link
        )
    }


}