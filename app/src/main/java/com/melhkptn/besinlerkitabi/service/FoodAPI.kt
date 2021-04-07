package com.melhkptn.besinlerkitabi.service

import com.melhkptn.besinlerkitabi.model.Food
import io.reactivex.Single
import retrofit2.http.GET

interface FoodAPI  {

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getFoodList() : Single<List<Food>>
}
