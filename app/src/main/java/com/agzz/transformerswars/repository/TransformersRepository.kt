package com.agzz.transformerswars.repository

import com.agzz.transformerswars.models.Transformer
import com.agzz.transformerswars.models.Transformers
import com.agzz.transformerswars.networking.Resource
import kotlinx.coroutines.flow.Flow

interface TransformersRepository {
   suspend fun getTransformers(): Flow<Resource<Transformers>>
   suspend fun createTransformer(transformer: Transformer): Flow<Resource<Transformers>>
   suspend fun updateTransformer(transformer: Transformer): Flow<Resource<Transformers>>
   suspend fun deleteTransformer(transformerId:String): Flow<Resource<Transformers>>
}