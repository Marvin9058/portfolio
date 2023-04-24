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
import com.howl.howlstagram_f16.navigation.HomeFragment
import kotlinx.android.synthetic.main.item_alcohol_item.view.*

class HomeAlcoholAdapter(
    val context: Context,
    var alcoholList: ArrayList<Alcohol>,
    var activity: Activity
) : RecyclerView.Adapter<HomeAlcoholAdapter.HomeAlcoholViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAlcoholViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_alcohol_item, parent, false)

        return HomeAlcoholViewHolder(view)

    }

    override fun onBindViewHolder(holder: HomeAlcoholViewHolder, position: Int) {

        holder.bind(alcoholList[position])
    }

    override fun getItemCount(): Int {
        return alcoholList.size
    }

    inner class HomeAlcoholViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var alcoholImage : ImageView = itemView.alcohol_item_image
        var alcoholName : TextView = itemView.alcohol_item_text


        fun bind(alcohol : Alcohol){

            Glide.with(this.itemView)
                .load(alcohol.imageUrl)
                .error(R.drawable.ic_baseline_emoji_food_beverage_24)
                .into(alcoholImage)

            alcoholName.text = alcohol.name

            alcoholImage.setOnClickListener {
                val next = Intent(context, AlcoholDetailActivity::class.java)
                next.putExtra("alcohol_item", alcohol)
                context.startActivity(next)
                activity.overridePendingTransition(R.anim.sliding_up, R.anim.stay)


            }

        }
    }
}