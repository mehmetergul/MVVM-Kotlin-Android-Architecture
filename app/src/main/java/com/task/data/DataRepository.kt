package com.task.data

import com.task.data.local.LocalRepository
import com.task.data.remote.RemoteRepository
import com.task.data.remote.Data
import io.reactivex.Single
import javax.inject.Inject


class DataRepository @Inject
constructor(private val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) : DataSource {
    override fun requestCountries(hashMap : HashMap<String, Any>): Single<Data> {
        return remoteRepository.requestCountries(hashMap)
    }

}
