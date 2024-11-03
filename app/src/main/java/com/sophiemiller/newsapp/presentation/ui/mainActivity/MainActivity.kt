package com.sophiemiller.newsapp.presentation.ui.mainActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.sophiemiller.newsapp.presentation.ui.composeScreens.MainActivityFragments
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.NewsAppSharedViewModel
import com.sophiemiller.newsapp.presentation.ui.themes.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * main startup activity that holds all the available screens
 *
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<NewsAppSharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                MainActivityFragments(viewModel)
            }
        }
        lifecycleScope.launch {
            viewModel.openUrlEvent.collectLatest { url ->
                withContext(Dispatchers.Main) {
                    openArticle(url)
                }
            }
        }
    }

    private fun openArticle(url: String?) {
        url?.let {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            this.startActivity(intent)
        } ?: run {
            Toast.makeText(
                this,
                "No link available",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}