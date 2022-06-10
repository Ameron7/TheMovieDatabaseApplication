package id.indocyber.api_service.service.remote.movie

import id.indocyber.common.base.Constant
import id.indocyber.common.entity.movie.discover.MovieDiscoverResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDiscoverService {
    @GET("discover/movie")
    suspend fun getMovieByGenre(
        @Query("with_genres") genres: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = Constant.API_KEY
    ): Response<MovieDiscoverResponse>
}