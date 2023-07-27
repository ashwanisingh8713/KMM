package com.myapplication

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import data.datasource.ApiRequest
import data.repository.SectionsListRepoImpl
import domain.usecase.SectionListUseCase
import ui.vm.SectionListViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiRequest = ApiRequest()
        val sectionsListRepoImpl = SectionsListRepoImpl(apiRequest)
        val sectionListUseCase = SectionListUseCase(sectionsListRepoImpl)
        val sectionListViewModel = SectionListViewModel(sectionListUseCase)

        setContent {
            MainView(sectionListViewModel)
        }
    }
}