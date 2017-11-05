package com.mobile.app.data

import com.mobile.app.data.models.ApiResponse
import com.mobile.app.data.models.MoviesDetailsModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    fun getMovies(
            @Query("page") page: Int
    ): Single<ApiResponse>

    @GET("movie/{movie_id}")
    fun getMoveDetails(@Path("movie_id") id: Int): Single<MoviesDetailsModel>
}