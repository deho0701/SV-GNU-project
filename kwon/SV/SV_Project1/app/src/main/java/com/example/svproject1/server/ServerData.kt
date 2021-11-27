package com.example.svproject1.server

import com.google.gson.annotations.SerializedName

data class TableNumData(
    @SerializedName("store_name")
    val name: String,
    val tableNum: Int,
)

data class CafeData(
    @SerializedName("store_name")
    val name: String,
    @SerializedName("table_id")
    val tableId: Int,
    val tableX: Float,
    val tableY: Float
)

data class BookData(
    @SerializedName("id")
    val bookerId: String,
    @SerializedName("store_name")
    val cafeName: String,
    val tables: ArrayList<Int>,
    val time: String
)

data class HistoryNumData(
    @SerializedName("store_name")
    val id: String,
    val historyNum: Int,
)

data class HistoryData(
    @SerializedName("id")
    val id: String,
    @SerializedName("store_name")
    val cafeName: String,
    val date: String
)