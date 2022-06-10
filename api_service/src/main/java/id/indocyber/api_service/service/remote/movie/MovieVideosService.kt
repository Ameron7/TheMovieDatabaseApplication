package id.indocyber.api_service.service.remote.movie

import id.indocyber.common.base.Constant
import id.indocyber.common.entity.movie.video.MovieVideosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieVideosService {
    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = Constant.API_KEY
    ): Response<MovieVideosResponse>
}