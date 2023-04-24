package com.howl.howlstagram_f16.adapter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RatingAlcohol(

    var imageUrl: String? = null,
    var name: String? = null,
    var rating: Float? = null
) : Parcelable