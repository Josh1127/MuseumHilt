package com.eric.museumhilt.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eric.museumhilt.data.OperationResult
import com.eric.museumhilt.model.Museum
import com.eric.museumhilt.model.MuseumDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MuseumViewModel @ViewModelInject constructor (private val repository:MuseumDataSource):ViewModel() {

    private val _museums = MutableLiveData<List<Museum>>().apply { value = emptyList() }
    val museums: LiveData<List<Museum>> = _museums

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<Any>()
    val onMessageError:LiveData<Any> = _onMessageError

    private val _isEmptyList=MutableLiveData<Boolean>()
    val isEmptyList:LiveData<Boolean> = _isEmptyList

    fun loadMuseums(){
        _isViewLoading.postValue(true)

        viewModelScope.launch {
            var result:OperationResult<Museum> = withContext(Dispatchers.IO){
                repository.retrieveMuseums()
            }
            _isViewLoading.postValue(false)
            when(result){
                is OperationResult.Success ->{
                    if(result.data.isNullOrEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        _museums.value = result.data
                    }
                }
                is OperationResult.Error ->{
                    _onMessageError.postValue(result.exception)

                }
            }
        }

    }
}