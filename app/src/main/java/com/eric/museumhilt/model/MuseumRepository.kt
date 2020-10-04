package com.eric.museumhilt.model

import com.eric.museumhilt.data.ApiClient
import com.eric.museumhilt.data.OperationResult

class MuseumRepository:MuseumDataSource {
    override suspend fun retrieveMuseums(): OperationResult<Museum> {
        try {
            val response = ApiClient.build()?.museums()
            response?.let {
                return if(it.isSuccessful && it.body()!=null){
                    val data = it.body()?.data
                    OperationResult.Success(data)
                }else{
                    val message = it.body()?.msg
                    OperationResult.Error(Exception(message))
                }
            }?:run{
                return OperationResult.Error(Exception("Occurred un error"))
            }
        }catch (e:Exception){
            return OperationResult.Error(e)
        }
    }
}