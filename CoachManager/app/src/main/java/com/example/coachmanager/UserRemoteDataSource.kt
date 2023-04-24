package com.example.coachmanager

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemoteDataSource {
    fun findAll(onResult: (List<User>?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(UserService::class.java)
        retrofit.findAll().enqueue(
            object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    val users = response.body()
                    onResult(users)
                }
            }
        )
    }

    fun findById(id: Int, onResult: (User?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(UserService::class.java)
        retrofit.findById(id).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user = response.body()
                    onResult(user)
                }
            }
        )
    }
}