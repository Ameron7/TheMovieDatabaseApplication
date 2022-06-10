package id.indocyber.themoviedatabaseapplication.fragments.movie_discover

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.base.BaseFragment
import id.indocyber.common.entity.movie.discover.Result
import id.indocyber.themoviedatabaseapplication.R
import id.indocyber.themoviedatabaseapplication.databinding.LayoutMovieDiscoverFragmentBinding
import id.indocyber.themoviedatabaseapplication.fragments.paging_state.PagingStateAdapter
import id.indocyber.themoviedatabaseapplication.view_model.MovieDiscoverViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDiscoverFragment :
    BaseFragment<LayoutMovieDiscoverFragmentBinding, MovieDiscoverViewModel>() {
    override val layoutResourceId: Int = R.layout.layout_movie_discover_fragment
    override val vm: MovieDiscoverViewModel by viewModels()
    val adapter = MovieDiscoverPagingAdapter(::moveFragment)
    val pagingStateAdapter = PagingStateAdapter(::retryAction)
    val args by navArgs<MovieDiscoverFragmentArgs>()

    override fun initBinding() = with(binding) {
        super.initBinding()
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error && adapter.itemCount == 0) {
                retryButton.visibility = View.VISIBLE
                recycler.visibility = View.GONE
                progressBar.visibility = View.GONE
            } else if (it.refresh is LoadState.Loading && adapter.itemCount == 0) {
                retryButton.visibility = View.GONE
                recycler.visibility = View.GONE
                progressBar.visibility = View.VISIBLE

            } else if (it.refresh is LoadState.NotLoading) {
                retryButton.visibility = View.GONE
                recycler.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                if (adapter.itemCount == 0) {
                    noMovie.visibility = View.VISIBLE
                }
            }
        }
        retryButton.setOnClickListener {
            retryAction()
        }
        recycler.adapter = adapter.withLoadStateFooter(pagingStateAdapter)
    }

    override fun observeLiveData() = with(vm) {
        observePagingData(
            data = pagingData,
            success = {
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.submitData(it)
                }
            }
        )
        val genres = args.genreId.map {
            it.toString()
        }.toTypedArray()
        getData(genres)
    }

    fun moveFragment(movie: Result) {
        vm.toMovieDetails(movie)
    }

    fun retryAction() {
        adapter.retry()
    }
}