package com.agzz.transformerswars.ui.transformers.list

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.agzz.transformerswars.Constants
import com.agzz.transformerswars.Constants.*
import com.agzz.transformerswars.models.overallRating
import com.agzz.transformerswars.models.Transformer
import com.agzz.transformerswars.models.Transformers
import com.agzz.transformerswars.networking.Resource
import com.agzz.transformerswars.networking.Status
import com.agzz.transformerswars.repository.TransformersRepository
import com.agzz.transformerswars.ui.SingleLiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.abs

class TransformersListViewModel @ViewModelInject constructor(
    private val transformersRepository: TransformersRepository
) : ViewModel() {

    private val _displayTransformersList = MutableLiveData<Resource<Transformers>>()
    val displayTransformersList: LiveData<Resource<Transformers>> get() = _displayTransformersList
    val displayTransformersWar = SingleLiveEvent<String>()


    init {
        getTransformers()
    }

    private fun getTransformers() {
        viewModelScope.launch {
            transformersRepository.getTransformers().collect {
                _displayTransformersList.value = it
            }
        }
    }

    fun deleteTransformer(transformerId:String){
        viewModelScope.launch {
            transformersRepository.deleteTransformer(transformerId).collect {
                    _displayTransformersList.value = it
            }
        }
    }


    fun startWar(){
        var battles = 0
        var allDestroyed = false
        var aVictory = 0
        var dVictory = 0

      _displayTransformersList.value?.data?.transformers?.let { transformers ->
          var sortedList = transformers.sortedByDescending { it.rank }
          val autobots = sortedList.filter { transformer ->  transformer.team == "A" }
          val finalAutobots = autobots.toMutableList()
          val decepticons = sortedList.filter { transformer ->  transformer.team == "D" }
          val finalDecepticons = decepticons.toMutableList()
          if (autobots.size > decepticons.size){
              for (i in decepticons.indices){
                  battles++
                when(battle(autobots[i], decepticons[i])){
                    BattleResult.ALLDESTROYED ->{
                       allDestroyed = true
                    }
                    BattleResult.BOTHDESTROYED ->{

                    }
                    BattleResult.AUTOBOTWIN ->{
                        aVictory++
                        finalDecepticons.remove(decepticons[i])
                    }
                    BattleResult.DECEPTICONWIN ->{
                        dVictory++
                        finalAutobots.remove(autobots[i])
                    }
                }
              }
          }
          else{
              for (i in autobots.indices){
                  battles++
                  when(battle(autobots[i], decepticons[i])){
                      BattleResult.ALLDESTROYED ->{
                          allDestroyed = true
                      }
                      BattleResult.BOTHDESTROYED ->{

                      }
                      BattleResult.AUTOBOTWIN ->{
                          aVictory++
                          finalDecepticons.remove(decepticons[i])
                      }
                      BattleResult.DECEPTICONWIN ->{
                          dVictory++
                          finalAutobots.remove(autobots[i])
                      }
                  }
              }
          }
          val autobotsString = if (finalAutobots.isEmpty()) "None" else finalAutobots.joinToString { it.name }
          val decepticonsString = if(finalDecepticons.isEmpty()) "None" else finalDecepticons.joinToString { it.name }
          var result = ""
          when {
              allDestroyed -> {
                  result = "All transformers are destroyed after a fierce battle between Optimus Prime and Predaking"
              }
              aVictory == dVictory -> {
                  result = "The battle ended in a draw"
              }
              else -> {
                  when (aVictory.compareTo(dVictory)) {
                      0 -> {
                          result = "The battle ended in a draw"
                      }
                      1 -> {
                          result = "$battles battles \n Winning team (Autobots): $autobotsString \n Survivors from the losing team (Decepticons): $decepticonsString"
                      }
                      -1 -> {
                          result = "$battles battles \n Winning team (Decepticons): $decepticonsString \n Survivors from the losing team (Autobots): $autobotsString"
                      }
                  }
              }
          }
          displayTransformersWar.value =  result
      }
    }

    //Battle between 2 transformers, first parameter is an autobot and second parameter is a decepticon
    private fun battle(a:Transformer, d:Transformer): BattleResult{
        when {
            a.name == Constants.optimusprime -> {
                return if (d.name == Constants.predaking) BattleResult.ALLDESTROYED else BattleResult.AUTOBOTWIN
            }
            d.name == Constants.predaking -> return BattleResult.DECEPTICONWIN
            else -> {
                return if (abs(a.courage - d.courage) >= 4) {
                    if (a.courage > d.courage) BattleResult.AUTOBOTWIN else BattleResult.DECEPTICONWIN
                } else if (abs(a.strength - d.strength) >= 3) {
                    if (a.courage > d.courage) BattleResult.AUTOBOTWIN else BattleResult.DECEPTICONWIN
                } else    {
                    when {
                        a.overallRating() == d.overallRating() -> BattleResult.BOTHDESTROYED
                        a.overallRating() > d.overallRating() -> BattleResult.AUTOBOTWIN
                        else -> BattleResult.DECEPTICONWIN
                    }
                }
            }
        }
    }
}