package com.example.testserver

import com.google.gson.annotations.SerializedName

data class TestServerData(
    //val users: List<User>
    @SerializedName("ID")
    val ID: Int,
    val table_num: Int,
    val x: Int,
    val y: Int
)

data class TableNumData(
    @SerializedName("ID")
    val ID: Int,
    val table_num: Int,
)


