package di

import data.repository.SectionContentRepoImpl
import data.repository.SectionsListRepoImpl
import domain.repository.SectionContentRepo
import domain.repository.SectionsListRepo
import domain.usecase.SectionContentUseCase
import domain.usecase.SectionListUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import ui.vm.SectionListViewModel

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            viewModelModule,
            useCasesModule,
            repositoryModule,
            ktorModule,
            dispatcherModule,
        )
    }



val viewModelModule = module {
    factory { SectionListViewModel(get(), get()) }
}

val useCasesModule: Module = module {
    factory { SectionContentUseCase(get()) }
    factory { SectionListUseCase(get()) }
}

val repositoryModule = module {
    single<SectionContentRepo> { SectionContentRepoImpl(get(), get()) }
    single<SectionsListRepo> { SectionsListRepoImpl(get(), get()) }

}

val ktorModule = module {
    single {
        HttpClient() {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })

            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
                filter { request ->
                    request.url.host.contains("ktor.io")
                }
                sanitizeHeader { header ->
                    header == HttpHeaders.Authorization
                }
            }

            engine {
                // this: HttpClientEngineConfig
                threadsCount = 4
                pipelining = true
            }
        }
    }

    single { "https://app.thehindu.com/hindu/service/api_v1.006" }
}



val dispatcherModule = module {
    factory { Dispatchers.Default }
}



fun initKoin() = initKoin {}



