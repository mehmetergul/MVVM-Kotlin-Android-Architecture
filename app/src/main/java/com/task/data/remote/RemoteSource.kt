package com.task.data.remote

import io.reactivex.Observable
import io.reactivex.Single


internal interface RemoteSource {
    fun requestCountries(hashMap : HashMap<String, Any>): Observable<Data>

}
