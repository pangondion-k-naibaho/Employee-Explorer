package com.employeeexplorer.client.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EmployeeResponse(
    @field: SerializedName("uuid")
    val uuid: String?= "",

    @field:SerializedName("full_name")
    val fullName: String?= "",

    @field: SerializedName("phone_number")
    val phoneNumber: String?= "",

    @field:SerializedName("email_address")
    val emailAddress: String?= "",

    @field:SerializedName("biography")
    val biography: String?= "",

    @field:SerializedName("photo_url_small")
    val photoUrlSmall:String?= "",

    @field:SerializedName("photo_url_large")
    val photoUrlLarge: String?= "",

    @field: SerializedName("team")
    val team: String?= "",

    @field:SerializedName("employee_type")
    val employeeType: String?= ""
):Parcelable