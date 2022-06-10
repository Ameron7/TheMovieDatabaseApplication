package id.indocyber.common.entity.movie.discover


import com.google.gson.annotations.SerializedName

data class MovieDiscoverResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)