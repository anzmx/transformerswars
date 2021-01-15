package com.agzz.transformerswars.data.remote

import com.agzz.transformerswars.models.Transformer
import com.agzz.transformerswars.models.Transformers
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("allspark")
    suspend fun getAllSpark(): Response<String>

    @GET("transformers")
    suspend fun getTransformers(): Response<Transformers>

    @POST("transformers")
    suspend fun createTransformer(@Body transformer: Transformer): Response<Transformer>

    @PUT("transformers")
    suspend fun updateTransformer(@Body transformer: Transformer): Response<Transformer>

    @DELETE("transformers/{Id}")
    suspend fun deleteTransformer(@Path("Id") transformerId: String): Response<ResponseBody>
}