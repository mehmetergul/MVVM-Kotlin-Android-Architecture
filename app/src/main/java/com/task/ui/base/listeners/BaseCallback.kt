package com.task.ui.base.listeners

import com.task.data.remote.Error


interface BaseCallback<in T : Any> {
    fun onSuccess(data: T?)

    fun onFail(error : Error?)
}
