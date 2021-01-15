package com.agzz.transformerswars.data.remote

import com.agzz.transformerswars.models.Transformer
import com.agzz.transformerswars.models.Transformers
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService): ApiHelper {
    override suspend fun getTransformers(): Response<Transformers> = apiService.getTransformers()
    override suspend fun getAllSpark(): Response<String> = apiService.getAllSpark()
    override suspend fun createTransformer(transformer: Transformer): Response<Transformer> = apiService.createTransformer(transformer)
    override suspend fun updateTransformer(transformer: Transformer): Response<Transformer> = apiService.updateTransformer(transformer)
    override suspend fun deleteTransformer(string: String): Response<ResponseBody> = apiService.deleteTransformer(string)
}