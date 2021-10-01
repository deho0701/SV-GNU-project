package com.example.sv_project1.dto

import retrofit2.Call
import retrofit2.http.*

data class ResponseDTO(var result:String? = null)

interface APIInterface {
    @GET("/")
    fun getRequest(@Query("name") name: String): Call<ResponseDTO>

    @FormUrlEncoded
    @POST("/")
    fun postRequest(@Field("id")id: String,
                    @Field("password")password: String):Call<ResponseDTO>

    @FormUrlEncoded
    @PUT("/{id}")
    fun putRequest(@Path("id")id: String,
                   @Field("content")content: String): Call<ResponseDTO>

    @DELETE("/{id}")
    fun deleteRequest(@Path("id")id: String): Call<ResponseDTO>
}