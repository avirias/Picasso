package com.avirias.picasso.domain.util

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateConvertor @Inject constructor(
    private val simpleDateFormat: SimpleDateFormat
) {

    fun convertStringToDate(
        date: String
    ): Date = try {
        simpleDateFormat.parse(date)!!
    } catch (e: Exception) {
        Date()
    }
}