package com.task.ui.component.countries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.task.R
import com.task.data.remote.model.CountriesItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_item.*

/**
 * @author mehmetergul on 15/01/2020
 * Copyright (c) 2020. All rights reserved.
 */

class CountriesAdapter(private val countries: List<CountriesItem>) : RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {

 /*   private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(countriesItem: CountriesItem) {
            //newsListViewModel.openNewsDetails(newsItem)
        }
    }
    */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return CountriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    class CountriesViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(countriesItem: CountriesItem) {
            tv_caption.text = countriesItem.name
            tv_title.text = countriesItem.code

            if (!countriesItem.flagUrl.isNullOrEmpty()) {
                Picasso.get().load(countriesItem.flagUrl).placeholder(R.drawable.mvvm).error(R.drawable.mvvm).into(iv_news_item_image)
            }
        //    rl_news_item.setOnClickListener { recyclerItemListener.onItemSelected(countriesItem) }
        }
    }
}

