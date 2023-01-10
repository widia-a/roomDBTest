package com.bcaf.roomdbtest.service

import com.bcaf.roomdbtest.model.PostDummyData
import com.bcaf.roomdbtest.model.ResponsePostDummyData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DummyApiInterface {
//    @Headers("app-id:63bcc99843b161608eeac665")
    @POST("post/create/")
    fun postData(@Body dummyData: PostDummyData): Call<ResponsePostDummyData>
}