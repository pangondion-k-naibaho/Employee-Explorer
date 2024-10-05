package com.employeeexplorer.client.ui.viewmodels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.employeeexplorer.client.data.repository.CollectionEmployeesRepository

class HomeViewModelFactory(
    private val collectionEmployeesRepository: CollectionEmployeesRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(collectionEmployeesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}