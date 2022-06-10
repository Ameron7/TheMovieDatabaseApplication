package id.indocyber.themoviedatabaseapplication.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.movie.GetMovieDiscoverPagingUseCase
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.entity.genre.Genre
import id.indocyber.common.entity.movie.discover.Result
import id.indocyber.themoviedatabaseapplication.fragments.movie_discover.MovieDiscoverFragmentDirections
import id.indocyber.themoviedatabaseapplication.fragments.movie_genres.MovieGenresFragmentDirections
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDiscoverViewModel @Inject constructor(
    application: Application,
    private val getMovieDiscoverPagingUseCase: GetMovieDiscoverPagingUseCase
) : BaseViewModel(application) {
    val pagingData = MutableLiveData<PagingData<Result>>()

    fun getData(genres: Array<String>) {
        if (pagingData.value == null) {

            viewModelScope.launch {
                getMovieDiscoverPagingUseCase.invoke(genres).flow.cachedIn(viewModelScope).collect {
                    pagingData.postValue(it)
                }
            }
        } else {
            pagingData.postValue(pagingData.value)
        }
    }


    fun toMovieDetails(movie: Result) {
        navigate(
            MovieDiscoverFragmentDirections.actionMovieDiscoverFragmentToMovieDetailsFragment(
                movie.id
            )
        )
    }
}