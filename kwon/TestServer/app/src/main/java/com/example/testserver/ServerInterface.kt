package com.example.testserver

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServerInterface {
    @GET("sit")
    fun getUser(
        @Query("table_id") table_id:Int
        //@Query("table_x") table_x: Int,
        //@Query("table_y") table_y: Int
    ): Call<TestServerData>
    @GET("table_num")
    fun getTableNum(
        //@Query("ID") id:Int
        //@Query("table_num") table_num: Int
    ): Call<TableNumData>

    @POST("pay")
    fun postBookData(
        @Body bookData: BookData
    ): Call<BookData>
}