package id.indocyber.themoviedatabaseapplication.fragments.movie_genres

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.base.BaseFragment
import id.indocyber.common.entity.genre.Genre
import id.indocyber.themoviedatabaseapplication.R
import id.indocyber.themoviedatabaseapplication.activities.RootActivity
import id.indocyber.themoviedatabaseapplication.databinding.LayoutGenresFragmentBinding
import id.indocyber.themoviedatabaseapplication.view_model.MovieGenresViewModel

@AndroidEntryPoint
class MovieGenresFragment : BaseFragment<LayoutGenresFragmentBinding, MovieGenresViewModel>() {
    override val layoutResourceId: Int = R.layout.layout_genres_fragment
    override val vm: MovieGenresViewModel by viewModels()
    val adapter =
        MovieGenresAdapter{ vm.selectionTracker?.isSelected(it) ?: false }

    override fun initBinding() = with(binding) {
        recycler.adapter = adapter
        retryButton.setOnClickListener {
            vm?.getData()
        }
        createTracker()
        btnConfirm.setOnClickListener {
            vm?.toDiscoverMovie()
        }
    }

    override fun observeLiveData() = with(vm) {


        observeResponseData(
            data = responseState,
            success = {
                adapter.submitData(it?.genres.orEmpty())
                binding.retryButton.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.btnConfirm.visibility = View.VISIBLE
            },
            error = {
                Log.i("AppResonse", "error")
                binding.retryButton.visibility = View.VISIBLE
                binding.btnConfirm.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            },
            loading = {
                Log.i("AppResonse", "loading")
                binding.retryButton.visibility = View.GONE
                binding.btnConfirm.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }
        )
    }


    private fun createTracker() {
        vm.selectionTracker = vm.selectionTracker?.let {
            SelectionTracker.Builder<Long>(
                "genreId", binding.recycler,
                adapter.getItemKeyProvider(), MovieGenresItemDetailsLookUp(binding.recycler),
                StorageStrategy.createLongStorage()
            ).withOnItemActivatedListener { itemDetails, event ->
                vm.selectionTracker?.select(itemDetails.selectionKey ?: 0)
                true
            }.withSelectionPredicate(SelectionPredicates.createSelectAnything()).build().apply {
                it.selection.forEach {
                    this.select(it)
                }
            }
        } ?: kotlin.run {
            SelectionTracker.Builder<Long>(
                "genreId", binding.recycler,
                adapter.getItemKeyProvider(), MovieGenresItemDetailsLookUp(binding.recycler),
                StorageStrategy.createLongStorage()
            ).withOnItemActivatedListener { itemDetails, event ->
                vm.selectionTracker?.select(itemDetails.selectionKey ?: 0)
                true
            }.withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()
        }
    }



}