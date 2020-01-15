package com.task.ui.component.splash

import android.os.Bundle
import android.os.Handler
import com.task.R
import com.task.data.remote.dto.CountriesModel
import com.task.databinding.ActivitySplashBinding
import com.task.ui.ViewModelFactory
import com.task.ui.base.BaseActivity
import com.task.ui.component.countries.CountriesActivity
import com.task.utils.Constants
import org.jetbrains.anko.startActivity
import javax.inject.Inject


class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(){

    @Inject
    override lateinit var viewModel: SplashViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    override val layoutId: Int
        get() = R.layout.activity_splash

    override val bindingVariable: Int
        get() = 1

    private var mActivitySplashBinding: ActivitySplashBinding? = null

    override fun bindingViewModel() {
        mActivitySplashBinding = super.viewDataBinding
        mActivitySplashBinding!!.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var countriesModel : CountriesModel = CountriesModel()
        countriesModel.status = "Mehmet Ergül"
        viewModel.countriesModel.value = countriesModel
        navigateToMainScreen()
    }

    private fun navigateToMainScreen() {
        Handler().postDelayed({
            startActivity<CountriesActivity>()
            finish()
        }, Constants.SPLASH_DELAY.toLong())
    }
}