package id.indocyber.api_service.usecase.movie

import id.indocyber.api_service.paging.MovieDiscoverDataSources
import id.indocyber.api_service.service.remote.movie.MovieDiscoverService


class GetMovieDiscoverPagingUseCase(private val movieDiscoverService: MovieDiscoverService) {
    operator fun invoke(genres : Array<String>) = MovieDiscoverDataSources.createPager(10, movieDiscoverService, genres)
}