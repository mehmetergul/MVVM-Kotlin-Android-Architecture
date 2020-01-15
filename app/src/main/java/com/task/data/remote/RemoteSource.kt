package com.task.data.remote

import io.reactivex.Single


internal interface RemoteSource {
    fun requestCountries(hashMap : HashMap<String, Any>): Single<Data>

}
