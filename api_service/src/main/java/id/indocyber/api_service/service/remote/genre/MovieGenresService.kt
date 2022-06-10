package id.indocyber.api_service.service.remote.genre

import id.indocyber.common.base.Constant
import id.indocyber.common.entity.genre.MovieGenresResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieGenresService {
    @GET("genre/movie/list")
    suspend fun getData(
        @Query("api_key") api_key: String = Constant.API_KEY
    ): Response<MovieGenresResponse>
}