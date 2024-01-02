package com.ns.shopify.di

import com.ns.shopify.data.storage.CachingManager
import org.koin.dsl.module

/**
 * Created by Ashwani Kumar Singh on 05,December,2023.
 */



actual fun platformModule() = module {
    single<CachingManager> {
        CachingManager()
    }
}