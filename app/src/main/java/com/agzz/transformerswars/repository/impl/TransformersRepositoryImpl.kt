package com.agzz.transformerswars.repository.impl

import com.agzz.transformerswars.data.remote.ApiHelper
import com.agzz.transformerswars.models.Transformer
import com.agzz.transformerswars.models.Transformers
import com.agzz.transformerswars.networking.Resource
import com.agzz.transformerswars.repository.TransformersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TransformersRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
) : TransformersRepository {

    private val responseTransformers = ArrayList<Transformer>()

    override suspend fun getTransformers(): Flow<Resource<Transformers>> {
        return flow{
            emit(Resource.loading(null))
            try {
                val req = apiHelper.getTransformers()
                if (req.isSuccessful) {
                    req.body()?.let { responseTransformers.addAll(it.transformers) }
                    emit(Resource.success(req.body()))
                }
                else emit(Resource.error(data = null, msg = req.errorBody()?.string()?:"Error fetching transformers"))
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    override suspend fun createTransformer(transformer: Transformer) : Flow<Resource<Transformers>> {
        return flow{
            emit(Resource.loading(null))
            try {
                val req = apiHelper.createTransformer(transformer)
                if (req.isSuccessful) {
                    req.body()?.let { responseTransformers.add(it) }
                    emit(Resource.success(Transformers(responseTransformers)))
                }
                else emit(Resource.error(data = null, msg = req.errorBody()?.string()?:"Error creating transformer"))
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    override suspend fun updateTransformer(transformer: Transformer) : Flow<Resource<Transformers>> {
        return flow{
            emit(Resource.loading(null))
            try {
                val req = apiHelper.updateTransformer(transformer)
                if (req.isSuccessful){
                    req.body()?.let { responseTransformers.find { transformer ->  transformer.id == it.id }.apply {
                        it.copy()
                    }}
                    emit(Resource.success(Transformers(responseTransformers)))
                }
                else emit(Resource.error(data = null, msg = req.errorBody()?.string()?:"Error updating transformer"))
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    override suspend fun deleteTransformer(transformerId: String) : Flow<Resource<Transformers>> {
        return flow{
            emit(Resource.loading(null))
            try {
                val req = apiHelper.deleteTransformer(transformerId)
                if (req.isSuccessful){
                    responseTransformers.remove(responseTransformers.find { it.id == transformerId })
                    emit(Resource.success(Transformers(responseTransformers)))
                }
                else emit(Resource.error(data = null, msg = req.errorBody()?.string()?:"Error deleting transformer"))
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }


}