package com.example.books.ui.home.fragments.webView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.books.core.Constants
import com.example.books.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebViewBinding
    private var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            url = bundle.getString(Constants.URL, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        println(
            "Webview$url"
        )
        webView()
        return binding.root
    }

    private fun webView() {
        val webView = binding.webView

        webView.settings.javaScriptEnabled = true

        val urlModified = url.replace("http://", "https://")
        webView.loadUrl(urlModified)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }
    }

}