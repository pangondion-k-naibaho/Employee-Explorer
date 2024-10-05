package com.employeeexplorer.client.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionEmployeesResponse(
    @field:SerializedName("employees")
    val employees: List<EmployeeResponse>?= null
):Parcelable