package com.agzz.transformerswars.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transformer(
    val id: String?,
    val name: String,
    val team: String,
    val strength: Int,
    val intelligence: Int,
    val speed: Int,
    val endurance: Int,
    val rank: Int,
    val courage: Int,
    val firepower: Int,
    val skill: Int,
    val team_icon: String?
): Parcelable

fun Transformer.overallRating(): Int{
  return strength + intelligence + speed + endurance + firepower
}