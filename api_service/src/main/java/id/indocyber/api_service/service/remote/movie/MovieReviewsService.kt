package id.indocyber.api_service.service.remote.movie

import id.indocyber.common.base.Constant
import id.indocyber.common.entity.movie.review.MovieReviewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieReviewsService {
    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constant.API_KEY
    ): Response<MovieReviewsResponse>
}