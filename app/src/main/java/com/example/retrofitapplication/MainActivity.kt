package com.example.retrofitapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitapplication.Adapters.CharacterAdapter
import com.example.retrofitapplication.Interfaces.CharacterInterface
import com.example.retrofitapplication.Models.Character
import com.example.retrofitapplication.Models.CharacterFeed
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var characters: MutableList<Character>
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var rvCharacters: RecyclerView
    private lateinit var characterInterface: CharacterInterface
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mGson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create(mGson))
            .build()

        this.initUi()
        this.fetchCharacters()
    }

    private fun initUi() {
        rvCharacters = findViewById(R.id.rvCharacters)
        rvCharacters.layoutManager = LinearLayoutManager(this)
        rvCharacters.setHasFixedSize(true)

        characters = ArrayList()

        characterAdapter = CharacterAdapter(characters, this)

        rvCharacters.adapter = characterAdapter

        characterInterface = retrofit.create(CharacterInterface::class.java)
    }

    private fun fetchCharacters() {
        val call: Call<CharacterFeed> = characterInterface.getCharacters()

        call.enqueue(object: Callback<CharacterFeed> {
            override fun onResponse(call: Call<CharacterFeed>, response: Response<CharacterFeed>) {
                if (response.isSuccessful) {
                    val feed: CharacterFeed? = response.body()
                    characters.addAll(feed!!.results)
                    characterAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<CharacterFeed>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }
        })
    }
}