package com.example.svproject.server

import com.google.gson.annotations.SerializedName

data class LoginData(
    val id: String,
    val pw: String
)

data class TableNumData(
    @SerializedName("store_name")
    val name: String,
    @SerializedName("table_num")
    val tableNum: Int
)

data class CafeData(
    @SerializedName("store_name")
    val name: String,
    @SerializedName("table_id")
    val tableId: Int,
    @SerializedName("table_x")
    val tableX: Float,
    @SerializedName("table_y")
    val tableY: Float,
    val booked: Boolean
)

data class BookData(
    @SerializedName("id")
    val bookerId: String,
    @SerializedName("store_name")
    val cafeName: String,
    val tables: ArrayList<Int>,
    val date: String,
    val time: String
)

data class HistoryNumData(
    @SerializedName("store_name")
    val id: String,
    @SerializedName("history_num")
    val historyNum: Int,
)

data class HistoryData(
    @SerializedName("id")
    val id: String,
    @SerializedName("store_name")
    val cafeName: String,
    val date: String,
    val time: Int
)