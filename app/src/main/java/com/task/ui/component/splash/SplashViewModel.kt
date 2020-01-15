package com.task.ui.component.splash

import androidx.lifecycle.MutableLiveData
import com.task.data.remote.dto.CountriesModel
import com.task.ui.base.BaseViewModel
import javax.inject.Inject


class SplashViewModel @Inject
constructor() : BaseViewModel(){
    var countriesModel: MutableLiveData<CountriesModel> = MutableLiveData()

}
