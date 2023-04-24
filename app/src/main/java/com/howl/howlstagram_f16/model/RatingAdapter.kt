package com.howl.howlstagram_f16.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.howl.howlstagram_f16.R
import com.howl.howlstagram_f16.navigation.rating_alcohol

class RatingAdapter(
    val ratingList : ArrayList<rating_alcohol>
) : RecyclerView.Adapter<RatingAdapter.ratingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ratingHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rating_rv, parent, false)

        return ratingHolder(view)
    }

    override fun getItemCount(): Int {
        return ratingList.size
    }

    override fun onBindViewHolder(holder: RatingAdapter.ratingHolder, position: Int) {

        holder.ratingImage.setImageResource(ratingList.get(position).rating_img)
        holder.ratingName.text = ratingList.get(position).rating_name
        holder.ratingValue.rating = ratingList.get(position).rating_value

    }



    inner class ratingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ratingImage = itemView.findViewById<ImageView>(R.id.rating_img)
        val ratingName = itemView.findViewById<TextView>(R.id.rating_name)
        val ratingValue = itemView.findViewById<RatingBar>(R.id.ratingBar)
    }

}