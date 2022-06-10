package id.indocyber.api_service.usecase.genre

import id.indocyber.api_service.service.remote.genre.MovieGenresService
import id.indocyber.common.base.ResponseApp
import id.indocyber.common.entity.genre.MovieGenresResponse
import kotlinx.coroutines.flow.flow

class GetMovieGenresUseCase(private val genreService: MovieGenresService) {
      operator fun invoke() = flow<ResponseApp<MovieGenresResponse>> {
        emit(ResponseApp.loading())
        val result = genreService.getData()
        if (result.isSuccessful) {
            result.body()?.let {
                emit(ResponseApp.success(it))
            }
        } else {
            emit(ResponseApp.error(null, result.code(), result.errorBody()))
        }
    }
}