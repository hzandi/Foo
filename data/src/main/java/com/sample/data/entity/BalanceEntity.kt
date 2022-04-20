package com.sample.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "balances")
data class BalanceEntity(
    @SerializedName("symbol")
    var symbol: String,
    @SerializedName("value")
    var value: Double?
) {
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
