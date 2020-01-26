package com.task.ui.base

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.task.R
import com.task.ui.base.listeners.ActionBarView
import com.task.ui.base.listeners.BaseView
import com.task.utils.CommonUtils
import com.task.utils.Network
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.toolbar.*


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(), BaseView, ActionBarView, BaseFragment.Callback {

    private var mProgressDialog: ProgressDialog? = null

    var viewDataBinding: T? = null
        private set

    private var mViewModel: V? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val viewModel: V

    protected abstract fun bindingViewModel()

    val isNetworkConnected: Boolean
        get() = Network.isConnected(applicationContext)

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        performDataBinding()
        initializeToolbar()
    }

    override fun onStart() {
        super.onStart()
        bindingViewModel()

    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    private fun initializeToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar?.title = ""
        }
    }

    override fun setUpIconVisibility(visible: Boolean) {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(visible)
    }

    override fun setTitle(titleKey: String) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            val title = findViewById<TextView>(R.id.txt_toolbar_title)
            title?.text = titleKey
        }
    }

    override fun setSettingsIconVisibility(visibility: Boolean) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            val icon = findViewById<ImageView>(R.id.ic_toolbar_setting)
            icon?.visibility = if (visibility) VISIBLE else GONE
        }
    }

    override fun setRefreshVisibility(visibility: Boolean) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            val icon = findViewById<ImageView>(R.id.ic_toolbar_refresh)
            icon?.visibility = if (visibility) VISIBLE else GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showAlert(title:String, message: String, button: String, successListener: DialogInterface.OnClickListener) {
        this.showAlert(title,message,button,successListener,null,null,null,null)
    }

    fun showAlert(title:String, message: String, buttonOne: String, buttonOneListener: DialogInterface.OnClickListener?,
                  buttonTwo: String?, buttonTwoListener: DialogInterface.OnClickListener?,
                  buttonThree: String?, buttonThreeListener: DialogInterface.OnClickListener?) {

        val builder = AlertDialog.Builder(this@BaseActivity)

        // Set the alert dialog title
        builder.setTitle(title)

        // Display a message on alert dialog
        builder.setMessage(message)

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton(buttonOne){dialog, which ->
            // Do something when user press the positive button
            if(buttonOneListener != null) {
                buttonOneListener!!.onClick(dialog,which)
            } else {
                dialog.dismiss()
            }
        }

        if (buttonTwo != null) {
            builder.setNeutralButton(buttonTwo!!){dialog, which ->
                // Do something when user press the positive button
                if(buttonTwoListener != null) {
                    buttonTwoListener!!.onClick(dialog,which)
                }
            }
        }

        if (buttonThree != null) {
            builder.setNegativeButton(buttonThree!!){dialog, which ->
                // Do something when user press the positive button
                if(buttonTwoListener != null) {
                    buttonThreeListener!!.onClick(dialog,which)
                }
            }
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }


    fun openActivityOnTokenExpire() {

    }

    fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }

    open fun onRightButtonPressed() {
    }

    /**
     * Hides keyboard on touching outside of EditText
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val view = currentFocus
        val ret = super.dispatchTouchEvent(event)

        if (view is EditText) {
            val w = currentFocus
            val scrcoords = IntArray(2)
            w!!.getLocationOnScreen(scrcoords)
            val x = event.rawX + w.left - scrcoords[0]
            val y = event.rawY + w.top - scrcoords[1]

            if (event.action == MotionEvent.ACTION_UP && (x < w.left || x >= w.right
                            || y < w.top || y > w.bottom)) {
                hideKeyboard()
            }
        }
        return ret
    }


    fun showDialogFragment(f: DialogFragment, name: String) {
        try {
            val fm = supportFragmentManager
            f.show(fm, name)

        } catch (e: Exception) {
            //loginRegisterAnimation();
        }
    }


    fun setFragment(fragment: Fragment, layout: Int, addToBackStack: Boolean, bundle: Bundle?, entryAnimation: Boolean = true) {
        val fTransaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) {
            fTransaction.addToBackStack(null)
        }

        fTransaction.setCustomAnimations(if (entryAnimation) R.anim.slide_left else 0, 0)

        if (bundle != null)
            fragment.arguments = bundle

        if (fragment is BaseFragment<*, *> && fragment.getFragmentTag() != null) {
            fTransaction.replace(layout, fragment, fragment.getFragmentTag())
        } else {
            fTransaction.replace(layout, fragment)
        }

        fTransaction.commitAllowingStateLoss()
    }


    fun removeAllBackStack() {
        val fm = supportFragmentManager
        val count = fm.backStackEntryCount
        for (i in 0 until count) {
            fm.popBackStack()
        }
    }
}
