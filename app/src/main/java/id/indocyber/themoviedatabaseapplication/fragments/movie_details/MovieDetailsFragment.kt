package id.indocyber.themoviedatabaseapplication.fragments.movie_details

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.base.BaseFragment
import id.indocyber.themoviedatabaseapplication.R
import id.indocyber.themoviedatabaseapplication.databinding.LayoutMovieDetailsFragmentBinding
import id.indocyber.themoviedatabaseapplication.fragments.paging_state.PagingStateAdapter
import id.indocyber.themoviedatabaseapplication.view_model.MovieDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<LayoutMovieDetailsFragmentBinding, MovieDetailsViewModel>() {
    override val layoutResourceId: Int = R.layout.layout_movie_details_fragment
    override val vm: MovieDetailsViewModel by viewModels()
    val reviewPagingAdapter = MovieDetailsReviewsPagingAdapter()
    val pagingStateAdapter = PagingStateAdapter(::retryAction)
    val args by navArgs<MovieDetailsFragmentArgs>()

    override fun initBinding() = with(binding) {
        reviewPagingAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Error -> {
                    movieReviewRecycler.visibility = View.GONE
                    videoTrailer.visibility = View.GONE
                    genreText.visibility = View.GONE
                    releaseDateText.visibility = View.GONE
                    voteText.visibility = View.GONE
                    retryButton.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }

                is LoadState.Loading -> {
                    movieReviewRecycler.visibility = View.GONE
                    retryButton.visibility = View.GONE
                    videoTrailer.visibility = View.GONE
                    genreText.visibility = View.GONE
                    releaseDateText.visibility = View.GONE
                    voteText.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }

                is LoadState.NotLoading -> {
                    videoTrailer.visibility = View.VISIBLE
                    genreText.visibility = View.VISIBLE
                    releaseDateText.visibility = View.VISIBLE
                    voteText.visibility = View.VISIBLE
                    retryButton.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    if (reviewPagingAdapter.itemCount == 0) {
                        movieReviewRecycler.visibility = View.GONE
                        noReview.visibility = View.VISIBLE
                    } else {
                        movieReviewRecycler.visibility = View.VISIBLE
                    }
                }
            }
        }
        movieReviewRecycler.adapter = reviewPagingAdapter.withLoadStateFooter(pagingStateAdapter)

        retryButton.setOnClickListener {
            vm?.getData(args.movieId)
            reviewPagingAdapter.retry()
        }

    }

    override fun observeLiveData() = with(vm) {

        observePagingData(
            data = movieReviewsPagingData,
            success = {
                CoroutineScope(Dispatchers.Main).launch {
                    reviewPagingAdapter.submitData(it)
                }
            }
        )


        observeResponseData(
            data = movieVideoResponseState,
            success = {
                val listener = object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        val videoId = it?.key.orEmpty()
                        youTubePlayer.cueVideo(videoId, 0f)

                        val defaultPlayerUiController =
                            DefaultPlayerUiController(binding.videoTrailer, youTubePlayer)
                        binding.videoTrailer.setCustomPlayerUi(defaultPlayerUiController.rootView)
                    }
                }
                val option = IFramePlayerOptions.Builder().controls(0).build()
                binding.videoTrailer.initialize(listener, option)
            }, error = {
                binding.videoTrailer.visibility = View.GONE
                binding.noVideo.visibility = View.VISIBLE
            }
        )
        vm.getData(args.movieId)
    }

    fun retryAction() {
        reviewPagingAdapter.retry()
    }
}