package id.indocyber.themoviedatabaseapplication.dependency_injection.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import id.indocyber.api_service.service.remote.genre.MovieGenresService
import id.indocyber.api_service.service.remote.movie.MovieDetailsService
import id.indocyber.api_service.service.remote.movie.MovieDiscoverService
import id.indocyber.api_service.service.remote.movie.MovieReviewsService
import id.indocyber.api_service.service.remote.movie.MovieVideosService
import id.indocyber.api_service.usecase.genre.GetMovieGenresUseCase
import id.indocyber.api_service.usecase.movie.GetMovieDetailsUseCase
import id.indocyber.api_service.usecase.movie.GetMovieDiscoverPagingUseCase
import id.indocyber.api_service.usecase.movie.GetMovieReviewsPagingUseCase
import id.indocyber.api_service.usecase.movie.GetMovieVideosUsecase

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideMovieGenresUsecase(movieGenresService: MovieGenresService) =  GetMovieGenresUseCase(movieGenresService)

    @Provides
    fun provideMovieDiscoverUsecase(movieDiscoverService: MovieDiscoverService) = GetMovieDiscoverPagingUseCase(movieDiscoverService)

    @Provides
    fun provideMovieDetailsUsecase(movieDetailsService: MovieDetailsService) = GetMovieDetailsUseCase(movieDetailsService)

    @Provides
    fun provideMovieReviewUsecase(movieReviewsService: MovieReviewsService) = GetMovieReviewsPagingUseCase(movieReviewsService)

    @Provides
    fun provideMovieVideosUsecase(movieVideosService: MovieVideosService) = GetMovieVideosUsecase(movieVideosService)
}