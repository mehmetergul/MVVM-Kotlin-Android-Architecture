package com.task.ui.component.countries

import com.task.data.remote.model.CountriesModel
import com.task.ui.base.listeners.BaseCallback

/**
 * @author mehmetergul on 15/01/2020
 * Copyright (c) 2020. All rights reserved.
 */

interface CountriesUseCaseImpl {
    fun getCountries(callback: BaseCallback<CountriesModel>, id : String)

}