package di

import daniel.avila.rnm.kmm.data_cache.CacheDataImp
import daniel.avila.rnm.kmm.data_cache.sqldelight.SharedDatabase
import daniel.avila.rnm.kmm.data_remote.RemoteDataImp
import daniel.avila.rnm.kmm.data_remote.model.mapper.ApiCharacterMapper
import daniel.avila.rnm.kmm.di.platformModule
import daniel.avila.rnm.kmm.domain.IRepository
import daniel.avila.rnm.kmm.domain.interactors.GetCharacterUseCase
import daniel.avila.rnm.kmm.domain.interactors.GetCharactersFavoritesUseCase
import daniel.avila.rnm.kmm.domain.interactors.GetCharactersUseCase
import daniel.avila.rnm.kmm.domain.interactors.IsCharacterFavoriteUseCase
import daniel.avila.rnm.kmm.domain.interactors.SwitchCharacterFavoriteUseCase
import daniel.avila.rnm.kmm.presentation.ui.features.character_detail.CharacterDetailViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.characters.CharactersViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.characters_favorites.CharactersFavoritesViewModel
import daniel.avila.rnm.kmm.repository.ICacheData
import daniel.avila.rnm.kmm.repository.IRemoteData
import daniel.avila.rnm.kmm.repository.RepositoryImp
import data.repository.PremiumContentRepoImpl
import data.repository.SectionContentRepoImpl
import data.repository.SectionsListRepoImpl
import domain.repository.PremiumContentRepo
import domain.repository.SectionContentRepo
import domain.repository.SectionsListRepo
import domain.usecase.PremiumContentUseCase
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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import ui.viewModel.PremiumContentViewModel
import ui.viewModel.SectionListViewModel

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            viewModelModule,
            useCasesModule,
            repositoryModule,
            ktorModule,
            sqlDelightModule,
            mapperModule,
            dispatcherModule,
            platformModule()
        )
    }



val viewModelModule = module {
    ////////// Danial
    factory { CharactersViewModel(get()) }
    factory { CharactersFavoritesViewModel(get()) }
    factory { params -> CharacterDetailViewModel(get(), get(), get(), params.get()) }
    //////////


    factory { SectionListViewModel(get(), get()) }
    factory { PremiumContentViewModel(get()) }

}

val useCasesModule: Module = module {
    ////////// Danial
    factory { GetCharactersUseCase(get(), get()) }
    factory { GetCharactersFavoritesUseCase(get(), get()) }
    factory { GetCharacterUseCase(get(), get()) }
    factory { IsCharacterFavoriteUseCase(get(), get()) }
    factory { SwitchCharacterFavoriteUseCase(get(), get()) }

    //////////
    factory { SectionContentUseCase(get()) }
    factory { SectionListUseCase(get()) }
    factory { PremiumContentUseCase(get()) }
}

val repositoryModule = module {
    ////////// Danial
    single<IRepository> { RepositoryImp(get(), get()) }
    single<ICacheData> { CacheDataImp(get()) }
    single<IRemoteData> { RemoteDataImp(get(named("daniel")), get(), get()) }

    //////////
    single<SectionContentRepo> { SectionContentRepoImpl(get(named("thg")), get(), get()) }
    single<SectionsListRepo> { SectionsListRepoImpl(get(named("thg")), get()) }
    single<PremiumContentRepo> { PremiumContentRepoImpl(get(named("premium")), get()) }

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

    single<String>(named("thg")) {  "https://app.thehindu.com/hindu/service/api_v1.006" }
    single<String>(named("daniel")) { "https://rickandmortyapi.com" }
    single<String>(named("premium")) { "https://www.thehindu.com/premiumfeed/" }
}


val sqlDelightModule = module {
    single { SharedDatabase(get()) }
}

val dispatcherModule = module {
    factory<CoroutineDispatcher> { Dispatchers.Default }
}

val mapperModule = module {
    factory { ApiCharacterMapper() }
}



fun initKoin() = initKoin {}



