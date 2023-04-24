package com.example.coachmanager

data class User(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val age: Int,
    val weight: Int,
    val gender: Char,
    val training_factor: Int
)