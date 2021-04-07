package com.melhkptn.besinlerkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.melhkptn.besinlerkitabi.model.Food
import com.melhkptn.besinlerkitabi.service.FoodAPIService
import com.melhkptn.besinlerkitabi.service.FoodDatabase
import com.melhkptn.besinlerkitabi.util.PrivateSharedPrefences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application) : BaseViewModel(application) {

    val foodList = MutableLiveData<List<Food>>()
    val foodErrorMessage = MutableLiveData<Boolean>()
    val loadingProgressBar = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()
    private val foodAPIService = FoodAPIService()
    private val privateSharedPrefences = PrivateSharedPrefences(getApplication())
    private val updateTime = 10 * 60 * 1000 * 1000 * 1000L 

    fun refreshData() {

        val savedDate = privateSharedPrefences.takeTime()
        if (savedDate != null && savedDate > 0L && System.nanoTime() - savedDate < updateTime) {
            //Room'dan al
            getDataRoom()

        } else {
            //İnternetten al
            getDataOnline()
        }
    }

    fun refreshDataOnline()
    {
        getDataOnline()
    }

    private fun getDataRoom(){

        loadingProgressBar.value = true

        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            foodList.value = dao.getAllFood()
            foodList.value?.let {
                showFoodList(it)
            }
        }
    }

    private fun getDataOnline() {

        loadingProgressBar.value = true

        disposable.add(
            foodAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>() {
                    override fun onSuccess(t: List<Food>) {
                        saveRoom(t)
                    }

                    override fun onError(e: Throwable) {
                        foodErrorMessage.value = true
                        loadingProgressBar.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun showFoodList(t: List<Food>) {
        foodList.value = t
        foodErrorMessage.value = false
        loadingProgressBar.value = false
    }

    private fun saveRoom(listFood: List<Food>) {

        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFood()
            val uuidList =
                dao.insertAll(*listFood.toTypedArray())
            var index: Int = 0
            while (index < listFood.size) {
                listFood[index].uuid = uuidList[index].toInt()
                index++
            }
            showFoodList(listFood)
        }

        privateSharedPrefences.saveTime(System.nanoTime())
    }
}
