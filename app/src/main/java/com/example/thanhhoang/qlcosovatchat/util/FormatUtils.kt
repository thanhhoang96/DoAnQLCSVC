package com.example.thanhhoang.qlcosovatchat.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Copyright © AsianTech Co., Ltd
 * Created by at-hoaiphan on 10/1/18.
 */
object FormatUtils {
    /**
     * Get date in yyyy-MM-dd HH:mm:ss
     *
     * @param date input value
     */
    fun formatDateTime(date: Date): String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPANESE).format(date)

    /**
     * Get date in yyyyMMddHHmmss
     *
     * @param date input value
     */
    fun formatDateTimeLong(date: Date): String = SimpleDateFormat("yyyyMMddHHmmss", Locale.JAPANESE).format(date)

    /**
     * Get date in yyyy.MM.dd
     *
     * @param dateTime input value
     */
    fun formatDisplayDate(dateTime: String): String = dateTime.substring(0, 10).replace("-", ".")

    /**
     * Format number
     *
     * @param number input number
     */
    fun formatNumber(number: Long): String = NumberFormat.getNumberInstance(Locale.JAPANESE).format(number)
}
