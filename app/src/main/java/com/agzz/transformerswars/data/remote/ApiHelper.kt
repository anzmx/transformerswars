package com.agzz.transformerswars.data.remote

import com.agzz.transformerswars.models.Transformer
import com.agzz.transformerswars.models.Transformers
import com.agzz.transformerswars.networking.Resource
import okhttp3.ResponseBody
import retrofit2.Response

interface ApiHelper {
    suspend fun getTransformers(): Response<Transformers>
    suspend fun getAllSpark(): Response<String>
    suspend fun createTransformer(transformer: Transformer): Response<Transformer>
    suspend fun updateTransformer(transformer: Transformer): Response<Transformer>
    suspend fun deleteTransformer(string: String): Response<ResponseBody>
}