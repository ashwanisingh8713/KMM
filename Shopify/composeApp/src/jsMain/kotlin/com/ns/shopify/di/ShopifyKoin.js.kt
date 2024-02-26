package com.ns.shopify.di

import com.ns.shopify.data.storage.CachingManager
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Created by Ashwani Kumar Singh on 02,December,2023.
 */
actual fun platformModule(): Module {
    return module {
        single<CachingManager> {
            CachingManager()
        }
    }
}