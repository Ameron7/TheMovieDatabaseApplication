package id.indocyber.api_service.usecase.movie

import id.indocyber.api_service.service.remote.movie.MovieVideosService
import id.indocyber.common.base.ResponseApp
import kotlinx.coroutines.flow.flow

class GetMovieVideosUsecase(private val movieVideosService: MovieVideosService) {
    operator fun invoke(movieId: Int) = flow {
        emit(ResponseApp.loading())
        val result = movieVideosService.getMovieVideos(movieId)
        if (result.isSuccessful) {
            emit(result.body()?.results?.let { list ->
                val new = list.filter {
                    it.type == "Trailer"
                }
                if (new.isNotEmpty())
                    ResponseApp.success(new[0])
                else
                    ResponseApp.error(Exception("Data kosong. " + result.message()), -1, null)
            })
        } else {
            emit(ResponseApp.error(null, result.code(), result.errorBody()))
        }
    }
}