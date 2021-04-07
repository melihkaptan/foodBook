package com.melhkptn.besinlerkitabi.service

import com.melhkptn.besinlerkitabi.model.Food
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FoodAPIService {

    private val api = Retrofit.Builder()
        .baseUrl(Companion.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) 
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) 
        .build()
        .create(FoodAPI::class.java)

    fun getData() : Single<List<Food>> {
        return api.getFoodList()
    }

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/"
    }
}
