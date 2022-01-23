package com.sonu.imagesearchapp.api

import com.sonu.imagesearchapp.data.UnsplashPhoto

data class UnsplashResponse(
    val results: List<UnsplashPhoto>
)