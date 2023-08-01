package com.myapplication

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import data.datasource.ApiRequest
import data.repository.SectionContentRepoImpl
import data.repository.SectionsListRepoImpl
import domain.usecase.SectionContentUseCase
import domain.usecase.SectionListUseCase
import ui.vm.SectionListViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiRequest = ApiRequest()
        val sectionsListRepoImpl = SectionsListRepoImpl(apiRequest = apiRequest)
        val sectionListUseCase = SectionListUseCase(sectionsListRepo = sectionsListRepoImpl)
        val sectionContentRepoImpl = SectionContentRepoImpl(apiRequest = apiRequest)
        val sectionContentUseCase = SectionContentUseCase(sectionContentRepo = sectionContentRepoImpl)
        val sectionListViewModel = SectionListViewModel(sectionListUseCase = sectionListUseCase,
            sectionContentUseCase = sectionContentUseCase)

        setContent {
            MainView(sectionListViewModel)
        }
    }
}

@Composable
fun test() {
    val dd = LocalConfiguration.current
    val context = LocalContext.current
}
