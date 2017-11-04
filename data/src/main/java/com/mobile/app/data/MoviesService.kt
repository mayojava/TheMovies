package com.mobile.app.data

import com.mobile.app.data.models.MoviesDetailsModel
import com.mobile.app.data.models.PopularMoviesModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    fun getMovies(
            @Query("page") page: String
    ): Single<List<PopularMoviesModel>>

    @GET("movie/{movie_id}")
    fun getMoveDetails(@Path("movie_id") id: Int): Single<MoviesDetailsModel>
}