package com.task.data.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Parcelize
data class CountriesItem (
        @SerializedName("code")
        var code: String = "",
        @SerializedName("name")
        var name: String = "",
        @SerializedName("phoneCode")
        var phoneCode: String = "",
        @SerializedName("flagUrl")
        var flagUrl: String = "",
        @SerializedName("isActive")
        var isActive: Boolean,
        @SerializedName("id")
        var id: Int
) : Parcelable