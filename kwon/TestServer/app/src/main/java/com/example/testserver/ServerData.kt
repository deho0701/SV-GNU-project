package com.example.testserver

import com.google.gson.annotations.SerializedName
import java.util.*

data class TestServerData(
    //val users: List<User>
    @SerializedName("table_id")
    val table_id: Int,
    val table_x: Int,
    val table_y: Int
)

data class TableNumData(
    @SerializedName("ID")
    val ID: Int,
    val table_num: Int,
)

data class BookData(
    @SerializedName("ID")
    val booker_id: Int,
    val booker: String,
    val cafe_name: String,
    val peopleNum: Int,
    val table_num: Int,
    val time: String
)


