package com.employeeexplorer.client.data.repository

import com.employeeexplorer.client.data.model.response.CollectionEmployeesResponse

interface CollectionEmployeesRepository {
    suspend fun getEmployees(): Result<CollectionEmployeesResponse>
    suspend fun getMalformedEmployees(): Result<CollectionEmployeesResponse>
    suspend fun getEmptyEmployees(): Result<CollectionEmployeesResponse>
}