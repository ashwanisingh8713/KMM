package com.rocketreserver.vm

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.apollographql.apollo3.api.Optional
import com.ns.shopify.LaunchListQuery
import com.rocketreserver.http.apolloClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 30,November,2023.
 */
class LaunchListViewModel:ScreenModel, KoinComponent {

    private val _response = MutableSharedFlow<LaunchListQuery.Data>()
    val response: SharedFlow<LaunchListQuery.Data> get() = _response


    private val _sectionList = MutableStateFlow<List<LaunchListQuery.Launch>>(emptyList())
    val successSectionList: StateFlow<List<LaunchListQuery.Launch>> get() = _sectionList

    private val _sectionListLoading = MutableStateFlow<Boolean>( true)
    val sectionListLoading: StateFlow<Boolean> get() = _sectionListLoading

    private val _sectionListError = MutableStateFlow<String?>( null)
    val sectionListError: StateFlow<String?> get() = _sectionListError

    fun makeListRequest() {
        coroutineScope.launch(Dispatchers.IO) {
            val response = apolloClient.query(LaunchListQuery(Optional.present(""))).execute()
            val launchList = response?.data?.launches?.launches?.filterNotNull().orEmpty()
            _sectionList.emit(launchList)
        }
    }

}