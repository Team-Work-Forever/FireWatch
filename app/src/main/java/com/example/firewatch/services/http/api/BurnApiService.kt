package com.example.firewatch.services.http.api

import com.example.firewatch.services.http.contracts.GeoCollection
import com.example.firewatch.services.http.contracts.GeoFeature
import com.example.firewatch.services.http.contracts.burns.BurnAvailabilityResponse
import com.example.firewatch.services.http.contracts.burns.BurnResponse
import com.example.firewatch.services.http.contracts.burns.CreateBurnResponse
import com.example.firewatch.services.http.contracts.burns.DeleteBurnResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import java.math.BigDecimal

interface BurnApiService {
    @GET("burns")
    suspend fun getAll(
        @Query("search") search: String? = null,
        @Query("state") state: String? = null,
        @Query("sort") sort: String? = null,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
    ): Response<GeoCollection<BurnResponse>>

    @GET("burns/{id}")
    suspend fun getById(
        @Path("id") id: String
    ): Response<GeoFeature<BurnResponse>>

    @GET("burns/availability/{lat},{lon}")
    suspend fun getAvailability(
        @Path("lat") lat: BigDecimal,
        @Path("lon") lon: BigDecimal
    ): Response<BurnAvailabilityResponse>

    @GET("burns/types")
    suspend fun getTypes(): Response<Array<String>>

    @GET("burns/states")
    suspend fun getStates(): Response<Array<String>>

    @GET("burns/reasons")
    suspend fun getReasons(): Response<Array<String>>

    @POST("burns")
    suspend fun create(
        @Body() createRequest: MultipartBody
    ): Response<CreateBurnResponse>

    @PUT("burns/{id}")
    suspend fun update(
        @Path("id") id: String,
        @Body() updateRequest: MultipartBody
    ): Response<GeoFeature<BurnResponse>>

    @DELETE("burns/{id}")
    suspend fun delete(
        @Path("id") id: String
    ): Response<DeleteBurnResponse>
}