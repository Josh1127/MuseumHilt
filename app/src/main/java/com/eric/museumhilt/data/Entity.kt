package com.eric.museumhilt.data

import com.eric.museumhilt.model.Museum

data class MuseumResponse(val status:Int?,val msg:String?,val data:List<Museum>?){
    fun isSuccess():Boolean= (status==200)
}