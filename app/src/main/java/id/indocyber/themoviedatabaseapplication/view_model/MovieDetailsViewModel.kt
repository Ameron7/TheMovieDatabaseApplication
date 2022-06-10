package id.indocyber.themoviedatabaseapplication.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.movie.GetMovieDetailsUseCase
import id.indocyber.api_service.usecase.movie.GetMovieReviewsPagingUseCase
import id.indocyber.api_service.usecase.movie.GetMovieVideosUsecase
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.base.ResponseApp
import id.indocyber.common.entity.movie.detail.Genre
import id.indocyber.common.entity.movie.detail.MovieDetailResponse
import id.indocyber.common.entity.movie.review.Result
import id.indocyber.common.entity.movie.video.Result as Videos
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    application: Application,
    val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    val getMovieVideosUsecase: GetMovieVideosUsecase,
    val getMovieReviewsPagingUseCase: GetMovieReviewsPagingUseCase
) : BaseViewModel(application) {
    val movieDetailResponseState = MutableLiveData<ResponseApp<MovieDetailResponse>>()
    val movieVideoResponseState = MutableLiveData<ResponseApp<Videos>>()
    val movieReviewsPagingData = MutableLiveData<PagingData<Result>>()

    fun getData(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailsUseCase(movieId).collect {
                movieDetailResponseState.value = it
            }
        }
        viewModelScope.launch {
            getMovieVideosUsecase(movieId).collect {
                movieVideoResponseState.postValue(it)
            }
        }
        viewModelScope.launch {
            getMovieReviewsPagingUseCase(movieId).flow.collect {
                movieReviewsPagingData.value = it
            }
        }
    }



    fun genreListToNames(genres: List<Genre>?) =
        genres.orEmpty().map { it.name }.toTypedArray().joinToString(separator = ", ")


}