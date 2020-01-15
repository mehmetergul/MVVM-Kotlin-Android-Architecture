package com.task.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountriesModel(
        @SerializedName("status")
        var status: String = "",
        @SerializedName("statusDetail")
        var statusDetail: String = "",
        @SerializedName("statusDetailMessage")
        var statusDetailMessage: String = "",
        @SerializedName("countries")
        var countries: List<CountriesItem> = listOf()
) : Parcelable