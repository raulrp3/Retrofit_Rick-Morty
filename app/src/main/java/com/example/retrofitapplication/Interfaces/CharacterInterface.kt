package com.example.retrofitapplication.Interfaces

import com.example.retrofitapplication.Models.CharacterFeed
import retrofit2.Call
import retrofit2.http.GET

interface CharacterInterface {

    @GET("character/")
    fun getCharacters(): Call<CharacterFeed>
}