package com.sophiemiller.newsapp.presentation.ui.mainActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.sophiemiller.newsapp.R
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
            viewModel.shareArticle.collectLatest { article ->
                withContext(Dispatchers.Main) {
                    shareArticle(article?.link, article?.title)
                }
            }
        }
    }

    /**
     * try to open the articles url or inform user of an error
     *
     * @param url
     */
    private fun shareArticle(url: String?, title: String?) {
        url?.let { //todo xyz change to share
            try {
                val share = Intent(Intent.ACTION_SEND)
                share.setType("text/plain")
                share.putExtra(Intent.EXTRA_SUBJECT, "$title")
                share.putExtra(Intent.EXTRA_TEXT, "$url")
                startActivity(Intent.createChooser(share, "Share article"))
            } catch (e: Exception) {
                // Notify the user that no suitable app is available
                Toast.makeText(
                    this,
                    getString(R.string.no_application_can_handle_this_action), Toast.LENGTH_SHORT
                ).show()
            }
        } ?: run {
            Toast.makeText(
                this,
                getString(R.string.no_link_available),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}