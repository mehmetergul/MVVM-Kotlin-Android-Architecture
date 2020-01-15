package com.task.data

import com.task.data.remote.Data
import io.reactivex.Single


internal interface DataSource {
    fun requestCountries(hashMap : HashMap<String, Any>): Single<Data>

}
