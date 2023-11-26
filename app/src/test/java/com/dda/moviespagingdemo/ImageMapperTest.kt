package com.dda.moviespagingdemo

import com.dda.moviespagingdemo.utils.ImageMapper
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class ImageMapperTest{

    @Test
    fun getFullImageUrl(){
        val fullUrl = ImageMapper(imagePath = "").getFullImageUrl()
        assertEquals("https://image.tmdb.org/t/p/w300",fullUrl)
    }

    @Test
    fun getFullImageUrl_abcd(){
        val fullUrl = ImageMapper(imagePath = "/abcd.jpg").getFullImageUrl()
        assertEquals("https://image.tmdb.org/t/p/w300/abcd.jpg",fullUrl)
    }

}