package com.example.apppokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppokedex.databinding.ActivityMainBinding
import com.example.apppokedex.retrofit.PokemonApiService
import com.example.apppokedex.retrofit.response.PokemonResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var apiRetrofit: Retrofit
    private lateinit var pokemonAdapter: PokemonAdapter
    private var offset = 0
    private var limit = 20
    private var puedeCargar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiRetrofit=Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        pokemonAdapter= PokemonAdapter()
        binding.rvpokemon.layoutManager=GridLayoutManager(applicationContext, 3)
        binding.rvpokemon.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy>0){
                    val itemVisibles = binding.rvpokemon.layoutManager!!.childCount
                    val itemTotales = binding.rvpokemon.layoutManager!!.itemCount
                    val primerItemVisible=(binding.rvpokemon.layoutManager!! as GridLayoutManager)
                        .findFirstVisibleItemPosition()

                    if(puedeCargar){
                        if(itemVisibles + primerItemVisible >= itemTotales){
                            puedeCargar = false
                            offset += 20
                            obtenerPokemonesRetrofit()
                        }
                    }
                }
            }
        })
        binding.rvpokemon.adapter=pokemonAdapter
        obtenerPokemonesRetrofit()
    }

    private fun obtenerPokemonesRetrofit() {
        val service= apiRetrofit.create(PokemonApiService::class.java)
        val pokemonResponse=service.obtenerPokemones(offset,limit)

        pokemonResponse.enqueue(object :Callback<PokemonResponse>{
            override fun onResponse(
                call: Call<PokemonResponse>,
                response: Response<PokemonResponse>
            ) {
                puedeCargar = true
                pokemonAdapter.agregarPokemones(response.body()!!.results)
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}