package com.task.ui.component.countries

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.BR
import com.task.R
import com.task.data.remote.model.CountriesItem
import com.task.databinding.ActivityCountriesBinding
import com.task.ui.base.BaseActivity
import com.task.ui.base.BaseViewModel
import com.task.ui.base.listeners.RecyclerItemListener
import com.task.ui.component.countries.detail.CountriesDetailFragment
import com.task.utils.EspressoIdlingResource
import dagger.android.AndroidInjector
import kotlinx.android.synthetic.main.activity_countries.*
import org.jetbrains.anko.toast
import javax.inject.Inject
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector


/**
 * @author mehmetergul on 15/01/2020
 * Copyright (c) 2020. All rights reserved.
 */

class CountriesActivity : BaseActivity<ActivityCountriesBinding, CountriesViewModel>(), RecyclerItemListener, HasSupportFragmentInjector, CountriesNavigator {
    override fun onNavigateActivity() {
        Log.d("girdigirdi", "asdfghjklşi")
    }

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_countries

    override val viewModel: CountriesViewModel
        get() {
            return mViewModel
        }

    @Inject
    internal lateinit var mViewModel: CountriesViewModel

    private var mActivityCountriesBinding: ActivityCountriesBinding? = null

    override fun bindingViewModel() {
        mActivityCountriesBinding = super.viewDataBinding
        mActivityCountriesBinding!!.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        initializeCountriesList()
        init(viewModel)
    }

    private fun init(viewModel: CountriesViewModel) {

        viewModel.noInterNetConnection.observe(this, Observer {
            if (it) {
                tv_no_data.visibility = View.VISIBLE
                rl_news_list.visibility = View.GONE
                toast("Please check your Internet connection!")
             //   pb_loading.visibility = View.GONE
                hideLoading()
            }
        })

        viewModel.showError.observe(this, Observer {
            showDataView(false)
            toast("" + it?.description)
        })


        viewModel.countriesModel.observe(this, Observer { countriesModel ->
            // we don't need any null checks here for the adapter since LiveData guarantees that
            if (!(countriesModel?.countries.isNullOrEmpty())) {
                mActivityCountriesBinding!!.viewModel = viewModel
                val countriesAdapter = CountriesAdapter( countriesModel?.countries!!, this)
                rv_news_list.adapter = countriesAdapter
                showDataView(true)
            } else {
                showDataView(false)
                toast("some thing went wrong!")
            }
            EspressoIdlingResource.decrement()
        })
        getCountries()
    }

    private fun initializeCountriesList() {
        val layoutManager = LinearLayoutManager(this)
        rv_news_list.layoutManager = layoutManager
        rv_news_list.setHasFixedSize(true)
    }

    override fun onItemSelected(position: Int) =
            this.navigateToDetailsScreen(viewModel.countriesModel.value?.countries?.get(position)!!)

    private fun getCountries() {
        showLoading()
        tv_no_data.visibility = View.GONE
        rl_news_list.visibility = View.GONE
        EspressoIdlingResource.increment()
        viewModel.getCountries()
    }


    private fun navigateToDetailsScreen(countriesItem: CountriesItem) {
        var bundle : Bundle = Bundle()
        bundle.putParcelable("countriesItem", countriesItem)
        setFragment(CountriesDetailFragment(), R.id.fl_container, true, bundle)
    }


    private fun showDataView(show: Boolean) {
        tv_no_data.visibility = if (show) View.GONE else View.VISIBLE
        rl_news_list.visibility = if (show) View.VISIBLE else View.GONE
        hideLoading()
    }


}