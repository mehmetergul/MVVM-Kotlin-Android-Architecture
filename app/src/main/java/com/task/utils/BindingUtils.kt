/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.task.utils

import android.content.res.ColorStateList
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso


/**
 * Created by amitshekhar on 11/07/17.
 */


@BindingAdapter("app:imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    if (url != null && url != "") {
        Picasso.get().load(url).into(imageView)
    }
}

@BindingAdapter("app:itemPosition")
fun setItemPosition(viewPager: ViewPager, index: Int?) {
    if (index != null) {
        viewPager.currentItem = index
    }
}


@BindingAdapter("app:itemIconTint")
fun setItemIconTint(view: BottomNavigationView, color: ColorStateList?) {
    view.itemIconTintList = color
}

@BindingAdapter("app:itemTextColor")
fun setItemTextColor(view: BottomNavigationView, color: ColorStateList?) {
    view.itemTextColor = color
}

@BindingAdapter("app:onNavigationItemSelected")
fun setOnNavigationItemSelectedListener(
        view: BottomNavigationView, listener: BottomNavigationView.OnNavigationItemSelectedListener) {
    view.setOnNavigationItemSelectedListener(listener)
}

@BindingAdapter("app:onEditorSearchAction")
fun EditText.onEditorSearchAction(f: Function1<String, Unit>?) {
    if (f == null) setOnEditorActionListener(null)
    else setOnEditorActionListener { v, actionId, event ->

        val imeAction = when (actionId) {
            EditorInfo.IME_ACTION_SEARCH -> true
            else -> false
        }

        val keydownEvent = event?.keyCode == KeyEvent.KEYCODE_SEARCH

        if (imeAction or keydownEvent)
            true.also { f(v.editableText.toString()) }
        else false
    }}


