package com.task.ui.component.countries

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.task.data.remote.Error
import com.task.data.remote.model.CountriesModel

import com.task.ui.base.BaseViewModel
import com.task.ui.base.listeners.BaseCallback
import javax.inject.Inject

/**
 * @author mehmetergul on 15/01/2020
 * Copyright (c) 2020. All rights reserved.
 */

class CountriesViewModel @Inject
constructor(private val countriesUseCase: CountriesUseCase, private val activity: CountriesActivity) : BaseViewModel<CountriesNavigator>(){
    var countriesModel: MutableLiveData<CountriesModel> = MutableLiveData()
    var noInterNetConnection: MutableLiveData<Boolean> = MutableLiveData()
    var showError: MutableLiveData<Error> = MutableLiveData()

    fun getCountries() {
        countriesUseCase.getCountries(callback, "id")
        activity.applicationContext
    }

    private val callback = object : BaseCallback<CountriesModel> {

        override fun onSuccess(data: CountriesModel?) {
            countriesModel.value = data
            navigator.onNavigateActivity()
        }

        override fun onFail(error: Error?) {
            if (error?.code == -1) {
                noInterNetConnection.postValue(true)
            } else {
                showError.postValue(error)
            }

        }
    }

}