package com.melhkptn.besinlerkitabi.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class PrivateSharedPrefences {

    companion object {
        private val TIME = "time"
        private var sharedPrefences: SharedPreferences? = null

        @Volatile
        private var instance: PrivateSharedPrefences? = null
        private val lock = Any()

        operator fun invoke(context: Context): PrivateSharedPrefences =
            instance ?: synchronized(lock) {
                instance ?: createPrivateSharedPreferences(context).also {
                    instance = it
                }
            }

        private fun createPrivateSharedPreferences(context: Context): PrivateSharedPrefences {
            sharedPrefences = PreferenceManager.getDefaultSharedPreferences(context)
            return PrivateSharedPrefences()
        }
    }

    fun saveTime(time: Long) {
        sharedPrefences?.edit(commit = true) {
            putLong(TIME, time)
        }
    }

    fun takeTime() = sharedPrefences?.getLong(TIME, 0)
}