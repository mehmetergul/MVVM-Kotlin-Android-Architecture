package com.task.ui.component.countries.detail

import android.os.Bundle
import android.view.View
import com.task.R
import com.task.data.remote.model.CountriesItem
import com.task.databinding.FragmentCountriesDetailBinding
import com.task.ui.base.BaseFragment
import javax.inject.Inject

/**
 * @author mehmetergul on 16/01/2020
 * Copyright (c) 2020. All rights reserved.
 */
class CountriesDetailFragment: BaseFragment<FragmentCountriesDetailBinding, CountriesDetailViewModel>()  {

    override val bindingVariable: Int
        get() = 1

    override val layoutId: Int
        get() = R.layout.fragment_countries_detail

    override val viewModel: CountriesDetailViewModel
        get()  {
            return mViewModel
        }

    @Inject
    internal lateinit var mViewModel: CountriesDetailViewModel

    private var mFragmentCountriesDetailBinding: FragmentCountriesDetailBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            var countriesItem : CountriesItem = arguments!!["countriesItem"] as CountriesItem
            viewModel.countriesItem.value = countriesItem
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mFragmentCountriesDetailBinding = super.viewDataBinding
    }

    companion object {
        val TAG = CountriesDetailFragment::class.java.getSimpleName()
        fun newInstance(countriesItem: CountriesItem): CountriesDetailFragment {
            val args = Bundle()
            if (countriesItem != null) {
                args.putParcelable("countriesItem", countriesItem)
            }
            val fragment = CountriesDetailFragment()
            fragment.setArguments(args)
            return fragment
        }
    }
}