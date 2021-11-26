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
        @Query("id") id:Int
    ): Call<HistoryNumData>

    @GET("sit")
    fun getUser(
        @Query("name") name:String,
        @Query("table_id") table_id:Int
    ): Call<CafeData>

    @GET("history")
    fun getHistories(
        @Query("id") id:Int,
        @Query("history_num") histoty_num:Int
    ): Call<BookData>


    @POST("pay")
    fun postBookData(
        @Body bookData: BookData
    ): Call<BookData>
}