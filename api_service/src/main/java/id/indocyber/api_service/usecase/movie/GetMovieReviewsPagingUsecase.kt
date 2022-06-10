package id.indocyber.api_service.usecase.movie

import id.indocyber.api_service.paging.MovieReviewsDataSources
import id.indocyber.api_service.service.remote.movie.MovieReviewsService

class GetMovieReviewsPagingUseCase(private val movieReviewService: MovieReviewsService) {
    operator fun invoke(movieId : Int) = MovieReviewsDataSources.createPager(10, movieReviewService, movieId)
}