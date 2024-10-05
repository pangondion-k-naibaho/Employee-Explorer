package com.employeeexplorer.client.data.remote

import com.employeeexplorer.client.data.model.response.CollectionEmployeesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("employees.json")
    suspend fun getEmployees(): Response<CollectionEmployeesResponse>

    @GET("employees_malformed.json")
    suspend fun getMalformedEmployees(): Response<CollectionEmployeesResponse>

    @GET("employees_empty.json")
    suspend fun getEmptyEmployees(): Response<CollectionEmployeesResponse>
}