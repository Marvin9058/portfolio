package com.howl.howlstagram_f16.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.howl.howlstagram_f16.R
import com.howl.howlstagram_f16.model.Alcohol
import com.howl.howlstagram_f16.navigation.AlcoholDetailActivity
import kotlinx.android.synthetic.main.item_search.view.*

class SearchRvAlcoholAdapter(
    val context: Context,
    val searchList: ArrayList<Alcohol>,
    var activity : Activity
) : RecyclerView.Adapter<SearchRvAlcoholAdapter.SearchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false)

        return SearchHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {

        holder.bind(searchList[position])

    }

    override fun getItemCount(): Int {

        return searchList.size
    }

    inner class SearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var img : ImageView = itemView.alcohol_img
        var name : TextView = itemView.alcohol_name
        var type : TextView = itemView.alcohol_type


        fun bind(alcohol: Alcohol){

            Glide.with(this.itemView)
                .load(alcohol.imageUrl)
                .error(R.drawable.ic_baseline_emoji_food_beverage_24)
                .into(img)

            name.text = alcohol.name
            type.text = alcohol.type

            img.setOnClickListener {
                val next = Intent(context, AlcoholDetailActivity::class.java)
                next.putExtra("alcohol_item", alcohol)
                context.startActivity(next)
                activity.overridePendingTransition(R.anim.sliding_up, R.anim.stay)
            }
        }
    }
}