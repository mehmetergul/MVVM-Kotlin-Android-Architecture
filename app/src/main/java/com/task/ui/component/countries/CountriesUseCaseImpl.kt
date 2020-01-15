package com.task.ui.component.countries

import com.task.data.remote.model.CountriesModel
import com.task.ui.base.listeners.BaseCallback

interface CountriesUseCaseImpl {
    fun getCountries(callback: BaseCallback<CountriesModel>, id : String)

}