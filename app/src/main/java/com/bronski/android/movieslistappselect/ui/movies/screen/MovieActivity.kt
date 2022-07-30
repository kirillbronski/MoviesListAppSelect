package com.bronski.android.movieslistappselect.ui.movies.screen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bronski.android.movieslistappselect.databinding.ActivityMovieBinding
import com.bronski.android.movieslistappselect.ui.movies.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {

    private var _binding: ActivityMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MoviesViewModel>()
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder

    private val movieAdapter = MovieAdapter()

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy < 0 && !binding.scrollToTop.isShown) {
                binding.scrollToTop.show()
            } else if (dy >= 0 && binding.scrollToTop.isShown) {
                binding.scrollToTop.hide()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        _binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAdapter()
        observeMovies()
        observeLoadState()
        scrollToTop()
        swipeToRefreshData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpAdapter() = with(binding) {

        val footerAdapter = DefaultLoadStateAdapter()
        val adapterWithLoadState = movieAdapter.withLoadStateFooter(footerAdapter)

        movieRecycler.apply {
            adapter = adapterWithLoadState
            layoutManager = LinearLayoutManager(context)
            (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
            addOnScrollListener(this@MovieActivity.scrollListener)
        }
        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            loadStateView,
        )
    }

    private fun swipeToRefreshData() = with(binding) {
        swipeToRefresh.setOnRefreshListener {
            viewModel.swipeToUpdate()
            if (swipeToRefresh.isShown) {
                swipeToRefresh.isRefreshing = false
            }
        }
    }

    private fun scrollToTop() = with(binding) {
        scrollToTop.setOnClickListener {
            movieRecycler.postDelayed({
                movieRecycler.smoothScrollToPosition(0)
            }, 250)
        }
    }

    private fun observeMovies() {
        lifecycleScope.launchWhenStarted {
            viewModel.movies.collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    private fun observeLoadState() {
        lifecycleScope.launch {
            movieAdapter.loadStateFlow.debounce(200).collectLatest {
                mainLoadStateHolder.bind(it.refresh)
            }
        }
    }
}