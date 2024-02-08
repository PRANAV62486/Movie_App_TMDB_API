package com.dda.moviespagingdemo.utils

import java.text.ParseException
import java.text.SimpleDateFormat


/*
 * Created by Pranav Kumar on 2/8/2024, 11:11 PM
 * Copyright (c) 2024 Roinet Solutions. All rights reserved.
 */

class Utilities {
    companion object{
        fun formatDateString(dtStart: String?): String? {
            val format = SimpleDateFormat("yyyy-MM-dd")
            val format2 = SimpleDateFormat("dd MMM yyyy")
            var s = dtStart
            try {
                val date = format.parse(s)
                s = format2.format(date)
            } catch (e: ParseException) {
            }
            return s
        }
    }
}