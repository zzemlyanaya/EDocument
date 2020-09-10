package ru.zzemlyanaya.edocument.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface IServerService {
    @GET("")
    fun login()

    @GET("/getClientData")
    fun getDataByUserID(@Query("uid")uid: String,
                        @Query("readerId")readerId: String)
}