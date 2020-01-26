package com.task.ui.component.splash

import androidx.lifecycle.MutableLiveData
import com.task.data.remote.model.CountriesModel
import com.task.ui.base.BaseViewModel
import com.task.ui.component.countries.CountriesNavigator
import javax.inject.Inject


class SplashViewModel @Inject
constructor() : BaseViewModel<CountriesNavigator>(){
    var countriesModel: MutableLiveData<CountriesModel> = MutableLiveData()

}
