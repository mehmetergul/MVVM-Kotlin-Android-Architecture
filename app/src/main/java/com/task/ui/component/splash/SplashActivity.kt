package com.task.ui.component.splash

import android.os.Bundle
import android.os.Handler
import com.task.BR
import com.task.R
import com.task.data.remote.model.CountriesModel
import com.task.databinding.ActivitySplashBinding
import com.task.ui.base.BaseActivity
import com.task.ui.component.countries.CountriesActivity
import com.task.utils.Constants
import org.jetbrains.anko.startActivity
import javax.inject.Inject


class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(){

    @Inject
    internal lateinit var mViewModel: SplashViewModel

    override val layoutId: Int
        get() = R.layout.activity_splash

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModel: SplashViewModel
        get() {
            return mViewModel
        }

    private var mActivitySplashBinding: ActivitySplashBinding? = null

    override fun bindingViewModel() {
        mActivitySplashBinding = super.viewDataBinding
        mActivitySplashBinding!!.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var countriesModel = CountriesModel()
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