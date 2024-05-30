package com.example.firewatch.services.http.api

import com.example.firewatch.services.http.contracts.GeoCollection
import com.example.firewatch.services.http.contracts.GeoFeature
import com.example.firewatch.services.http.contracts.autarchy.AutarchyResponse
import com.example.firewatch.services.http.contracts.autarchy.CreateAutarchyResponse
import com.example.firewatch.services.http.contracts.autarchy.DeleteAutarchyResponse
import com.example.firewatch.services.http.contracts.burns.BurnResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface AutarchyApiService {
    @POST("autarchies")
    suspend fun create(
        @Body() createRequest: MultipartBody
    ): Response<CreateAutarchyResponse>

    @PUT("autarchies/{id}")
    suspend fun update(
        @Path("id") id: String,
        @Body() updateRequest: MultipartBody
    ): Response<GeoFeature<AutarchyResponse>>

    @GET("autarchies/{id}")
    suspend fun getById(
        @Path("id") id: String,
    ): Response<GeoFeature<AutarchyResponse>>

    @GET("autarchies")
    suspend fun getAll(
        @Query("search") search: String? = null,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
    ): Response<GeoCollection<AutarchyResponse>>

    @GET("autarchies/{id}/burns")
    suspend fun getAllBurns(
        @Path("id") id: String,
        @Query("search") search: String? = null,
        @Query("state") state: String? = null,
        @Query("startDate") startDate: LocalDate? = null,
        @Query("endDate") endDate: LocalDate? = null,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
    ): Response<GeoCollection<BurnResponse>>

    @DELETE("autarchies/{id}")
    suspend fun delete(
        @Path("id") id: String,
    ): Response<DeleteAutarchyResponse>

}