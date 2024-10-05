package com.employeeexplorer.client.ui.viewmodels.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.employeeexplorer.client.data.model.response.CollectionEmployeesResponse
import com.employeeexplorer.client.data.repository.CollectionEmployeesRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val collectionEmployeesRepository: CollectionEmployeesRepository
): ViewModel(){
    private val TAG = HomeViewModel::class.java.simpleName

    private var _collectionEmployeesResponse = MutableLiveData<CollectionEmployeesResponse>()
    val collectionEmployeesResponse : LiveData<CollectionEmployeesResponse> = _collectionEmployeesResponse

    private var _collectionOtherEmployeesResponse = MutableLiveData<CollectionEmployeesResponse>()
    val collectionOtherEmployeesResponse : LiveData<CollectionEmployeesResponse> = _collectionOtherEmployeesResponse

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private var _isFail = MutableLiveData<Boolean>()
    val isFail : LiveData<Boolean> = _isFail

    fun getEmployees(){
        _isLoading.value = true
        viewModelScope.launch {
            val result = collectionEmployeesRepository.getEmployees()
            if(result.isSuccess){
                _collectionEmployeesResponse.value = result.getOrNull()
                _isFail.value = false
                Log.d(TAG, "retrieve success")
            }else{
                _isFail.value = true
                Log.e(TAG, "${result.exceptionOrNull()?.message}")
            }
            _isLoading.value = false
        }
    }

    fun getMalformedEmployees(){
        _isLoading.value = true
        viewModelScope.launch {
            val result = collectionEmployeesRepository.getMalformedEmployees()
            if(result.isSuccess){
                _collectionOtherEmployeesResponse.value = result.getOrNull()
                _isFail.value = false
                Log.d(TAG, "retrieve success")
            }else{
                _isFail.value = true
                Log.e(TAG, "${result.exceptionOrNull()?.message}")
            }
            _isLoading.value = false
        }
    }

    fun getEmptyEmployees(){
        _isLoading.value = true
        viewModelScope.launch {
            val result = collectionEmployeesRepository.getEmptyEmployees()
            if(result.isSuccess){
                _collectionEmployeesResponse.value = result.getOrNull()
                _isFail.value = false
                Log.d(TAG, "retrieve success")
            }else{
                _isFail.value = true
                Log.e(TAG, "${result.exceptionOrNull()?.message}")
            }
            _isLoading.value = false
        }
    }
}