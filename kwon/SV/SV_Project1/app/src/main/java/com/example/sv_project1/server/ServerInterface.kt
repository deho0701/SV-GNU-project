package com.example.sv_project1.server

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServerInterface {
    @GET("table_num")
    fun getTableNum(
        @Query("name") name:String
    ): Call<TableNumData>

    @GET("history_num")
    fun getHistoryNum(
        @Query("id") id:String
    ): Call<HistoryNumData>

    @GET("sit")
    fun getUser(
        @Query("name") name:String,
        @Query("table_id") table_id:Int
    ): Call<CafeData>

    @GET("history")
    fun getHistories(
        @Query("id") id:String,
        @Query("history_id") history_id:Int
    ): Call<HistoryData>


    @POST("pay")
    fun postBookData(
        @Body bookData: BookData
    ): Call<BookData>
}