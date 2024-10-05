package com.employeeexplorer.client.data.repository

import com.employeeexplorer.client.data.model.response.CollectionEmployeesResponse
import com.employeeexplorer.client.data.remote.ApiService

class CollectionEmployeesRepositoryImpl(private val apiService: ApiService): CollectionEmployeesRepository {
    override suspend fun getEmployees(): Result<CollectionEmployeesResponse> {
        return try {
            val response = apiService.getEmployees()
            if(response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getMalformedEmployees(): Result<CollectionEmployeesResponse> {
        return try {
            val response = apiService.getMalformedEmployees()
            if(response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getEmptyEmployees(): Result<CollectionEmployeesResponse> {
        return try {
            val response = apiService.getEmptyEmployees()
            if(response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

}