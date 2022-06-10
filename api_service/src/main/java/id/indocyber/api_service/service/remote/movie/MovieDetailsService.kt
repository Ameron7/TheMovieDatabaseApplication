package id.indocyber.api_service.service.remote.movie

import id.indocyber.common.base.Constant
import id.indocyber.common.entity.movie.detail.MovieDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailsService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = Constant.API_KEY
    ): Response<MovieDetailResponse>
}