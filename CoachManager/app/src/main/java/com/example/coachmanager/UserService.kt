package com.example.coachmanager

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/users")
    fun findAll(): Call<List<User>>

    @GET("/api/v1/notification/{id}")
    fun findById(@Path("id") id: Int): Call<User>
}