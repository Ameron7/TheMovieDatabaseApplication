package id.indocyber.common.entity.genre


import com.google.gson.annotations.SerializedName

data class MovieGenresResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)