package com.task.di

import com.task.ui.component.countries.detail.CountriesDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author mehmetergul on 16/01/2020
 * Copyright (c) 2020. All rights reserved.
 */
@Suppress("unused")
@Module
abstract class FragmentModuleBuilder {

    @ContributesAndroidInjector
    abstract fun contributeCountriesDetailFragment(): CountriesDetailFragment

}


