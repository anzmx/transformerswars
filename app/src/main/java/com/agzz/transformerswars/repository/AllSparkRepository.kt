package com.agzz.transformerswars.repository

import com.agzz.transformerswars.models.Transformers
import com.agzz.transformerswars.networking.Resource
import kotlinx.coroutines.flow.Flow

interface AllSparkRepository {
    suspend fun getAllSpark():Flow<Resource<String>>

}