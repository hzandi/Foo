package com.sample.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.sample.data.db.converter.ExchangeRateTypeConverter

@Entity(tableName = "latest_ex_rates")
data class LatestExRateEntity(
    @SerializedName("success")
    @Ignore
    val success: Boolean?,
    @SerializedName("error")
    @Ignore
    val error: ErrorEntity?,
    @SerializedName("timestamp")
    val timestamp: String?,
    @SerializedName("base")
    val base: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("rates")
    var rates: HashMap<String, Double>?
) {
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor(
        id:Int,
        timestamp: String?,
        base: String?,
        date: String?,
        rates: HashMap<String, Double>?
    ) : this(null, null, timestamp, base, date, rates)
}

data class ErrorEntity(
    @SerializedName("code")
    val code: String?,
    @SerializedName("info")
    val info: String?
)

