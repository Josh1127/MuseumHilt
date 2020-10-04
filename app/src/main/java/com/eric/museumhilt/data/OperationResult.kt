package com.eric.museumhilt.data

interface OperationResult<out T> {
    data class Success<T>(val data: List<T>?) : OperationResult<T>
    data class Error(val exception:Exception?) : OperationResult<Nothing>
}