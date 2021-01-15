package com.agzz.transformerswars.ui.transformers.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.agzz.transformerswars.models.Transformer
import com.agzz.transformerswars.models.Transformers
import com.agzz.transformerswars.networking.Resource
import com.agzz.transformerswars.repository.TransformersRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CreateEditViewModel @ViewModelInject constructor(
    private val transformersRepository: TransformersRepository
) : ViewModel() {

    private val _resultCreateEdit = MutableLiveData<Resource<Transformers>>()
    val resultCreateEdit: LiveData<Resource<Transformers>> get() = _resultCreateEdit

    fun updateTransformer(transformer: Transformer){
        if (transformer.name.isNotEmpty()) {
            viewModelScope.launch {
                transformersRepository.updateTransformer(transformer).collect {
                    _resultCreateEdit.value = it
                }
            }
        }
        else{
            _resultCreateEdit.value = Resource.error(data = null, msg = "Transformer name can't be empty")
        }
    }

    fun createTransformer(transformer: Transformer){
        if (transformer.name.isNotEmpty()) {
            viewModelScope.launch {
                transformersRepository.createTransformer(transformer).collect {
                    _resultCreateEdit.value = it
                }
            }
        }
        else{
            _resultCreateEdit.value = Resource.error(data = null, msg = "Transformer name can't be empty")
        }
    }
}