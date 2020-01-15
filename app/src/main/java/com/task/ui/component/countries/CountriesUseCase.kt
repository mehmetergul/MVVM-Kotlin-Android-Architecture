package com.task.ui.component.countries

import com.task.data.DataRepository
import com.task.data.remote.Data
import com.task.data.remote.Error
import com.task.data.remote.model.CountriesModel
import com.task.ui.base.listeners.BaseCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountriesUseCase
@Inject
constructor(private val dataRepository: DataRepository) : CountriesUseCaseImpl {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getCountries(callback: BaseCallback<CountriesModel>, id : String) {
        val disposableSingleObserver = object : DisposableSingleObserver<Data>() {

            override fun onSuccess(data: Data) {
                callback.onSuccess(data.data as CountriesModel?)
            }

            override fun onError(throwable: Throwable) {
                callback.onFail(throwable as Error)
            }
        }
        if (!compositeDisposable?.isDisposed) {
            var hashMap : HashMap<String, Any>
                    = HashMap()
            hashMap.put("command", "content.getCountries")
            hashMap.put("appToken", "5545DFE89136338D78DBAC7AB9F62MAJ0GLOVO")
            hashMap.put("channel", "WEB")
            val newsModelSingle = dataRepository.requestCountries(hashMap)
            val newsDisposable = newsModelSingle.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith<DisposableSingleObserver<Data>>(disposableSingleObserver)
            compositeDisposable.add(newsDisposable)
        }
    }
}