package com.example.testserver

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerInterface {
    @GET("table_num")
    fun getTableNum(
        @Query("ID") id:Int
        //@Query("table_num") table_num: Int,
        //@Query("x") x: Int,
        //@Query("y") y: Int
    ): Call<TableNumData>

    @GET("sit")
    fun getUser(
        @Query("ID") id:Int
        //@Query("table_num") table_num: Int,
        //@Query("x") x: Int,
        //@Query("y") y: Int
    ): Call<TestServerData>
}