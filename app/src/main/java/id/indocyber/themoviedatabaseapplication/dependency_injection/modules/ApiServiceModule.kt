package id.indocyber.themoviedatabaseapplication.dependency_injection.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.indocyber.api_service.retrofit.RetrofitClient
import id.indocyber.api_service.service.remote.genre.MovieGenresService
import id.indocyber.api_service.service.remote.movie.MovieDetailsService
import id.indocyber.api_service.service.remote.movie.MovieDiscoverService
import id.indocyber.api_service.service.remote.movie.MovieReviewsService
import id.indocyber.api_service.service.remote.movie.MovieVideosService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context) = RetrofitClient.getRetrofit(context)

    @Provides
    @Singleton
    fun provideMovieGenresService(retrofit: Retrofit) = retrofit.create(MovieGenresService::class.java)

    @Provides
    @Singleton
    fun provideMovieDiscoverService(retrofit: Retrofit) = retrofit.create(MovieDiscoverService::class.java)

    @Provides
    @Singleton
    fun provideMovieDetailsService(retrofit: Retrofit) = retrofit.create(MovieDetailsService::class.java)

    @Provides
    @Singleton
    fun provideMovieReviewsService(retrofit: Retrofit) = retrofit.create(MovieReviewsService::class.java)

    @Provides
    @Singleton
    fun provideMovieVideosService(retrofit: Retrofit) = retrofit.create(MovieVideosService::class.java)




}