package com.example.svproject.server

import androidx.constraintlayout.widget.ConstraintLayout
import com.example.svproject.data.SitSelectData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServerInterface {
    @GET("table_num")
    fun getTableNum(
        @Query("name") name: String
    ): Call<TableNumData>

    @GET("history_num")
    fun getHistoryNum(
        @Query("id") id: String
    ): Call<HistoryNumData>

    @GET("sit")
    fun getUser(
        @Query("name") name: String,
        @Query("table_id") tableId: Int,
        @Query("date") date: String,
        @Query("time") time: String
    ): Call<CafeData>

    @GET("history")
    fun getHistories(
        @Query("id") id: String,
        @Query("history_id") historyId: Int
    ): Call<HistoryData>

    @POST("history_del")
    fun removeHistories(
        @Body historyData: HistoryData
    ): Call<HistoryData>

    @POST("pay")
    fun postBookData(
        @Body bookData: BookData
    ): Call<BookData>
}