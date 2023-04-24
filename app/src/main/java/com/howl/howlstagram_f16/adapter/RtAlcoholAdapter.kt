package com.howl.howlstagram_f16.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.howl.howlstagram_f16.R
import kotlinx.android.synthetic.main.activity_search_map.view.*
import kotlinx.android.synthetic.main.item_rating_rv.view.*


class RtAlcoholAdapter(
    val context: Context,
    var rt_Alcohol : ArrayList<RatingAlcohol>
): RecyclerView.Adapter<RtAlcoholAdapter.RtAlcoholViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RtAlcoholViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rating_rv, parent, false)

        return RtAlcoholViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rt_Alcohol.size
    }

    override fun onBindViewHolder(holder: RtAlcoholViewHolder, position:Int) {

        holder.bind(rt_Alcohol[position])

        val item = rt_Alcohol[position]

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
        holder.apply {
            bind(item)
        }
    }

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    class RtAlcoholViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var alcoholImage : ImageView = itemView.rating_img
        var alcoholName : TextView = itemView.rating_name
        var alcoholRating : RatingBar = itemView.alcohol_ratingBar
        var ratingvaluetext : TextView = itemView.rb_Value


        fun bind(rtalcohol : RatingAlcohol){

            Glide.with(this.itemView)
                .load(rtalcohol.imageUrl)
                .error(R.drawable.ic_baseline_emoji_food_beverage_24)
                .into(alcoholImage)

            alcoholName.text = rtalcohol.name
            alcoholRating.rating = rtalcohol.rating!!.toFloat()



            ratingvaluetext.text = alcoholRating.rating.toString()


            ratingvaluetext.text = alcoholRating.getRating().toString()


        }

    }


}