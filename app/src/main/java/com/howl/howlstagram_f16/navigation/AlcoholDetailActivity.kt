package com.howl.howlstagram_f16.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.howl.howlstagram_f16.R
import com.howl.howlstagram_f16.model.Alcohol
import kotlinx.android.synthetic.main.activity_alcohol_detail.*
import kotlinx.android.synthetic.main.item_alcohol_item.*
import kotlinx.android.synthetic.main.item_alcohol_item.alcohol_item_image

class AlcoholDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alcohol_detail)
        val alcoholItem = intent.getParcelableExtra<Alcohol>("alcohol_item")

        Glide.with(this)
            .load(alcoholItem.imageUrl)
            .error(R.drawable.ic_baseline_emoji_food_beverage_24)
            .into(alcohol_item_image)

        alcohol_item_name.text = alcoholItem.name
        alcohol_item_type.text = alcoholItem.type
        alcohol_item_amount.text = alcoholItem.amount
        alcohol_item_alcohol.text = alcoholItem.alcohol
        alcohol_item_point.text = alcoholItem.point

        back_btn.setOnClickListener {
            finish()
        }
    }

}