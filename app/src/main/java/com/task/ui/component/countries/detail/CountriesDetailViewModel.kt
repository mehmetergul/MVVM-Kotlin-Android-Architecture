package com.task.ui.component.countries.detail

import androidx.lifecycle.MutableLiveData
import com.task.data.remote.model.CountriesItem
import com.task.data.remote.model.CountriesModel
import com.task.ui.base.BaseViewModel
import com.task.ui.component.countries.CountriesNavigator
import com.task.ui.component.countries.CountriesUseCase
import javax.inject.Inject

/**
 * @author mehmetergul on 16/01/2020
 * Copyright (c) 2020. All rights reserved.
 */
class CountriesDetailViewModel
@Inject
constructor(private val countriesUseCase: CountriesUseCase) : BaseViewModel<CountriesNavigator>(){

    var countriesItem: MutableLiveData<CountriesItem> = MutableLiveData()

}