package com.melhkptn.besinlerkitabi.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.melhkptn.besinlerkitabi.model.Food

@Database(entities = [Food::class], version = 1)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDAO

    //Database de conflict çıkmaması için Singleton yapacağız.
    companion object {

        @Volatile private var instance : FoodDatabase? = null
        //Volatile farklı threadlere database nesnesini görünür kılmak için eklenir.

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FoodDatabase::class.java,
                "foodDatabase"
            ).build()
    }
}