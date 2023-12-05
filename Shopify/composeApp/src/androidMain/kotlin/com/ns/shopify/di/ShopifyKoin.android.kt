package com.ns.shopify.di

import org.koin.dsl.module
import com.ns.shopify.data.storage.CachingManager

/**
 * Created by Ashwani Kumar Singh on 05,December,2023.
 */

actual fun platformModule() = module {

    single {
        CachingManager(get())
    }
}