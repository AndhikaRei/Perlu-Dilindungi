package com.example.perludilindungi.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.perludilindungi.databinding.FragmentDetailNewsBinding


/**
 * Displays a [WebView] containing covid19 news.
 */
class DetailNewsFragment : Fragment() {
    companion object {
        const val URL = "url"
    }

    private var _binding: FragmentDetailNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve url from Fragment arguments
        arguments?.let {
            url = it.getString(URL).toString()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        this.setHasOptionsMenu(true);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Configure the [WebView].
        val webView = binding.webView
        webView.webViewClient =  WebViewClient()

        // Load the [WebView].
        webView.loadUrl(url)

        // Change basic webview setting.
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.safeBrowsingEnabled = true

        // Handle on back pressed.
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(webView.canGoBack()){
                    webView.goBack()
                }else{
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Frees the binding object when the Fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}