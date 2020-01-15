package com.task.data.remote

import com.task.data.remote.model.CountriesModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import kotlin.collections.HashMap


interface ApiInterface {

    @POST("getRequest")
    fun  fetchCountries(@Body body :HashMap<String, Any>): Call<CountriesModel>
}
