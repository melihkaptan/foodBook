package com.melhkptn.besinlerkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.melhkptn.besinlerkitabi.model.Food
import com.melhkptn.besinlerkitabi.service.FoodDatabase
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application) : BaseViewModel(application ) {

    val foodLiveData = MutableLiveData<Food>()

    fun getRoomData(foodId: Int){
        launch {

            val dao = FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(foodId)
            foodLiveData.value = food
        }

    }
}