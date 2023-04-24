package com.howl.howlstagram_f16.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Alcohol(
    var alcohol: String? = null,
    var amount: String? = null,
    var imageUrl: String? = null,
    var name: String? = null,
    var point: String? = null,
    var type: String? = null
) : Parcelable
