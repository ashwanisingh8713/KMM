package com.app


/**
 * Created by Ashwani Kumar Singh on 04,December,2023.
 */
interface Platform {
    val name: String
}

expect fun getScreenSize(): Platform

