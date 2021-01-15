package com.agzz.transformerswars.ui.welcome

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.agzz.transformerswars.data.local.prefs.SharedPreferencesManager
import com.agzz.transformerswars.networking.Resource
import com.agzz.transformerswars.networking.Status
import com.agzz.transformerswars.repository.AllSparkRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WelcomeViewModel @ViewModelInject constructor(
    private val allSparkRepository: AllSparkRepository,
    private val preferencesManager: SharedPreferencesManager
) : ViewModel() {

    private val _resultAllSpark = MutableLiveData<Resource<String>>()
    val resultAllSpark: LiveData<Resource<String>> get() = _resultAllSpark

    fun getAllSpark(){
            viewModelScope.launch {
                allSparkRepository.getAllSpark().collect {
                    if (it.status == Status.SUCCESS){
                        preferencesManager.authToken = "Bearer " + it.data!!
                    }
                    _resultAllSpark.value = it
                }
            }
    }

}