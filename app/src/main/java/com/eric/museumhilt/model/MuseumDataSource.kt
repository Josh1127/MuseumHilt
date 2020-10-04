package com.eric.museumhilt.model

import com.eric.museumhilt.data.OperationResult

interface MuseumDataSource {
    suspend fun retrieveMuseums(): OperationResult<Museum>

}