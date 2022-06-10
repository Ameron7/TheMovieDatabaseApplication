package id.indocyber.themoviedatabaseapplication.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.selection.SelectionTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.genre.GetMovieGenresUseCase
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.base.ResponseApp
import id.indocyber.common.entity.genre.Genre
import id.indocyber.common.entity.genre.MovieGenresResponse
import id.indocyber.themoviedatabaseapplication.fragments.movie_genres.MovieGenresFragmentDirections
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieGenresViewModel @Inject constructor(
    application: Application,
    private val getMovieGenresUseCase: GetMovieGenresUseCase
) :
    BaseViewModel(application) {
    val responseState = MutableLiveData<ResponseApp<MovieGenresResponse>>()
    var selectionTracker: SelectionTracker<Long>? = null

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            getMovieGenresUseCase().collect {
                responseState.postValue(it)
            }
        }
    }

    fun toDiscoverMovie() {
        val genreList = selectionTracker?.selection?.toList()?.toLongArray()
        genreList?.let {
            navigate(
                MovieGenresFragmentDirections.actionMovieGenresFragmentToMovieDiscoverFragment(
                    it
                )
            )
        }
    }

}