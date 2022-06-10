package id.indocyber.api_service.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.indocyber.api_service.service.remote.movie.MovieDiscoverService
import id.indocyber.common.entity.movie.discover.Result

class MovieDiscoverDataSources(
    val movieDiscoverService: MovieDiscoverService,
    val genres: Array<String>
) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val result = movieDiscoverService.getMovieByGenre(
                genres.joinToString(separator = ","), page = params.key ?: 1
            )
            result.body()?.let {
                LoadResult.Page(
                    data = it.results,
                    if (it.page == 1)
                        null
                    else it.page.minus(1),
                    if (it.results.isEmpty())
                        null
                    else it.page.plus(1)
                )

            } ?: LoadResult.Error(Exception("invalid data"))

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        fun createPager(
            pageSize: Int,
            movieDiscoverService: MovieDiscoverService,
            genres: Array<String>
        ): Pager<Int, Result> = Pager(PagingConfig(pageSize), null) {
            MovieDiscoverDataSources(
                movieDiscoverService,
                genres
            )
        }
    }

}