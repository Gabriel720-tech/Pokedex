package com.example.apppokedex.retrofit

import com.example.apppokedex.retrofit.response.Pokemon
import com.example.apppokedex.retrofit.response.PokemonResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PokemonApiService {


    //METODOS:
    //LISTAR
    @GET("pokemon")
    fun obtenerPokemones(@Query("offset")offset:Int, @Query("limit")limit:Int): Call<PokemonResponse>

    /*REGISTRAR
    @POST("pokemon")
    fun registrarPokemon(@Body pokemon: Pokemon)

    //ACTUALIZAR
    @PUT("pokemon")
    fun actualizarPokemon(@Body pokemon: Pokemon)

    */

}


