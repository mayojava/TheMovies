package com.mobile.app.data.models

data class ApiResponse(
        val page: Int,
        val total_results: Int,
        val total_pages: Int,
        val results: List<PopularMoviesModel>
)