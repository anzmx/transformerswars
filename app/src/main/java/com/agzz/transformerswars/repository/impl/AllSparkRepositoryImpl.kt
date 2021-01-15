package com.agzz.transformerswars.repository.impl

import com.agzz.transformerswars.data.remote.ApiHelper
import com.agzz.transformerswars.networking.Resource
import com.agzz.transformerswars.repository.AllSparkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AllSparkRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) : AllSparkRepository {

    override suspend fun getAllSpark():Flow<Resource<String>> {
        return flow{
            emit(Resource.loading(null))
            try {
                val req = apiHelper.getAllSpark()
                if (req.isSuccessful) {
                    emit(Resource.success(req.body()))
                }
                else emit(Resource.error(data = null, msg = req.errorBody()?.string()?:"Error fetching AllSpark"))
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

}