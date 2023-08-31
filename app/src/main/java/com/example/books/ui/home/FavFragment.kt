package com.example.books.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.books.R
import com.example.books.databinding.FragmentFavBinding
import com.google.gson.Gson

class FavFragment : Fragment() {

    private lateinit var binding: FragmentFavBinding
    private var imagen : String? = null
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
        loadimage()
        return binding.root
    }

    private fun loadimage() {
        val httpsUrl = imagen?.replace("http://", "https://")
        Glide.with(requireContext())
            .load(httpsUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imageBook)
    }

    private fun loadSharedPreference() {
        val sharedPreferences = requireContext().getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE)
        imagen = sharedPreferences.getString("miArrayListKey", null)
    }

}