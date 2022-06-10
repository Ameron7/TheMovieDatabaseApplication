package id.indocyber.api_service.usecase.movie

import id.indocyber.api_service.service.remote.movie.MovieDetailsService
import id.indocyber.common.base.ResponseApp
import kotlinx.coroutines.flow.flow

class GetMovieDetailsUseCase(private val movieDetailService: MovieDetailsService) {
    operator fun invoke(movieId : Int) = flow {
        emit(ResponseApp.loading())

        val result = movieDetailService.getMovieDetails(movieId)
        if(result.isSuccessful) {
            emit(ResponseApp.success(result.body()))
        } else {
            emit(ResponseApp.error(null, result.code(), result.errorBody()))
        }
    }
}