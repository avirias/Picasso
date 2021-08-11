package com.avirias.picasso.data.network.util

import com.google.gson.Gson

object GsonFactory {

    @Volatile
    private var instance: Gson? = null

    fun getInstance(): Gson {
        return synchronized(this) {
            instance ?: Gson().also {
                instance = it
            }
        }
    }
}