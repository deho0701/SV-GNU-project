package com.example.sv_project1.server

import com.google.gson.annotations.SerializedName

data class TableNumData(
    @SerializedName("store_name")
    val name: String,
    val table_num: Int,
)

data class CafeData(
    @SerializedName("store_name")
    val name: String,
    @SerializedName("table_id")
    val table_id: Int,
    val table_x: Float,
    val table_y: Float
)

data class BookData(
    @SerializedName("id")
    val booker_id: String,
    @SerializedName("store_name")
    val cafe_name: String,
    val tables: ArrayList<Int>,
    val time: String
)

data class HistoryNumData(
    @SerializedName("store_name")
    val id: String,
    val history_num: Int,
)

data class HistoryData(
    @SerializedName("id")
    val id: String,
    @SerializedName("store_name")
    val cafe_name: String,
    val date: String
)