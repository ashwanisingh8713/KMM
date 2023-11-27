package com.ns.shopify.kmm.di

import com.ns.shopify.kmm.data_cache.sqldelight.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory(get()) }
}
