package com.task.ui.component.countries

import com.task.data.remote.dto.CountriesModel
import com.task.ui.base.listeners.BaseCallback

interface CountriesUseCaseImpl {
    fun getCountries(callback: BaseCallback<CountriesModel>)

}