package com.task.ui.component.countries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.task.R
import com.task.data.remote.model.CountriesItem
import com.task.ui.base.listeners.RecyclerItemListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_item.*

/**
 * @author mehmetergul on 15/01/2020
 * Copyright (c) 2020. All rights reserved.
 */

class CountriesAdapter(private val countries: List<CountriesItem>, onItemClickListener: RecyclerItemListener) : RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {
    private val onItemClickListener: RecyclerItemListener = onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return CountriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        holder.bind(countries[position], onItemClickListener, position)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    class CountriesViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(countriesItem: CountriesItem, recyclerItemListener: RecyclerItemListener, position: Int) {
            tv_caption.text = countriesItem.name
            tv_title.text = countriesItem.code

            if (!countriesItem.flagUrl.isNullOrEmpty()) {
                Picasso.get().load(countriesItem.flagUrl).placeholder(R.drawable.mvvm).error(R.drawable.mvvm).into(iv_news_item_image)
            }
            rl_news_item.setOnClickListener { recyclerItemListener.onItemSelected(position) }
        }
    }
}

