package com.shinjaehun.roomapp.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "user_table"
)
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
    @Embedded
    val address: Address // Embedded class도 Parcelize해야 정상 동작함!
): Parcelable

@Parcelize
data class Address (
    val streetName: String,
    val streetNumber: Int
): Parcelable