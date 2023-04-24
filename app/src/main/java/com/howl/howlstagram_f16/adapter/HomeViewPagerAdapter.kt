package com.howl.howlstagram_f16.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.howl.howlstagram_f16.R
import com.howl.howlstagram_f16.model.EventPage
import com.howl.howlstagram_f16.navigation.HomeFragment
import kotlinx.android.synthetic.main.item_home_viewpager.view.*

class HomeViewPagerAdapter(
    val context: Context,
    var pageList: ArrayList<EventPage>
) : RecyclerView.Adapter<HomeViewPagerAdapter.EventPageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventPageViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_home_viewpager, parent, false)

        return EventPageViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventPageViewHolder, position: Int) {
        holder.bind(pageList[position])
    }
    override fun getItemCount(): Int {
        return pageList.size
    }

    inner class EventPageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var imgItem : ImageView = itemView.vp_imageView


        fun bind(pageItem : EventPage) {

            Glide.with(this.itemView)
                .load(pageItem.imageUrl)
                .error(R.mipmap.ic_launcher)
                .into(imgItem)
        }
    }
}