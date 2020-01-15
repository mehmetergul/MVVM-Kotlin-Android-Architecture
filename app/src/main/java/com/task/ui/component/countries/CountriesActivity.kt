package com.task.ui.component.countries

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.R
import com.task.databinding.ActivityCountriesBinding
import com.task.ui.ViewModelFactory
import com.task.ui.base.BaseActivity
import com.task.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.activity_countries.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast
import javax.inject.Inject

class CountriesActivity : BaseActivity<ActivityCountriesBinding, CountriesViewModel>()  {

    override val bindingVariable: Int
        get() = 1

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

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
        initializeCountriesList()
        init(viewModel)
    }

    private fun init(viewModel: CountriesViewModel) {

        viewModel.noInterNetConnection.observe(this, Observer {
            if (it) {
                tv_no_data.visibility = View.VISIBLE
                rl_news_list.visibility = View.GONE
                toast("Please check your Internet connection!")
                pb_loading.visibility = View.GONE
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
                val countriesAdapter = CountriesAdapter( countriesModel?.countries!!)
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

   /* override fun onItemSelected(position: Int) =
            this.navigateToDetailsScreen(news = viewModel.newsModel.value?.newsItems?.get(position)!!)*/

    private fun getCountries() {
        pb_loading.visibility = View.VISIBLE
        tv_no_data.visibility = View.GONE
        rl_news_list.visibility = View.GONE
        EspressoIdlingResource.increment()
        viewModel.getCountries()
    }

    private fun navigateToDetailsScreen() {
     //   startActivity(intentFor<DetailsActivity>(Constants.NEWS_ITEM_KEY to news))
    }

    private fun showSearchError() {
        rl_news_list.snackbar(R.string.search_error)
    }

    private fun showDataView(show: Boolean) {
        tv_no_data.visibility = if (show) View.GONE else View.VISIBLE
        rl_news_list.visibility = if (show) View.VISIBLE else View.GONE
        pb_loading.visibility = View.GONE
    }


}