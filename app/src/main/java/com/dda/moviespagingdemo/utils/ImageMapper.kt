package com.dda.moviespagingdemo.utils


class ImageMapper(
    private val baseUrl: String = IMAGE_BASE_URL,
    private val imageSize: String = IMAGE_SIZE,
    private val imagePath: String
) {
    fun getFullImageUrl(): String {
        return baseUrl + imageSize + imagePath
    }
}
