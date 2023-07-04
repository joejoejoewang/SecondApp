package com.dragonlight.secondapp.db.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val contactId: Int,
    val contactName: String,
    val contactNumber: String
):Parcelable
