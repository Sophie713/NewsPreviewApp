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
        url?.let {
                val share = Intent(Intent.ACTION_SEND)
                share.setType("text/plain")
                share.putExtra(Intent.EXTRA_SUBJECT, "$title")
                share.putExtra(Intent.EXTRA_TEXT, "$url")
            // List of allowed package names
            val allowedPackages = listOf(
                "com.facebook.katana", // Facebook
                "com.twitter.android", // X (Twitter)
                "com.instagram.android" // Instagram
            )

            // Query all apps that can handle the intent
            val packageManager = this.packageManager
            val resolveInfoList = packageManager.queryIntentActivities(share, 0)

            // Filter to keep only allowed packages
            val filteredIntents = resolveInfoList
                .filter { it.activityInfo.packageName in allowedPackages }
                .map { resolveInfo ->
                    Intent(share).apply {
                        `package` = resolveInfo.activityInfo.packageName
                    }
                }.toMutableList()

            if (filteredIntents.isNotEmpty()) {
                // Create a chooser with the filtered intents
                val chooserIntent = Intent.createChooser(filteredIntents.removeAt(0), title).apply {
                    putExtra(Intent.EXTRA_INITIAL_INTENTS, filteredIntents.toTypedArray())
                }
                this.startActivity(chooserIntent)
            } else {
                // Handle case where no allowed apps are installed
                Toast.makeText(this, "No compatible apps installed", Toast.LENGTH_SHORT).show()
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