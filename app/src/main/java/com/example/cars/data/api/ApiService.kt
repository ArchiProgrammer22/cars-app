package com.example.cars.data.api

import com.example.cars.model.Cars
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("cars-api.php")
    suspend fun getCars() : Response<Cars>
}