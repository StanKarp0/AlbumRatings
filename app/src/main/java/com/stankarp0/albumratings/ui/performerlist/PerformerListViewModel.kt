package com.stankarp0.albumratings.ui.performerlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stankarp0.albumratings.services.PerformerObject
import com.stankarp0.albumratings.services.RandomApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PerformerListViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _performerObject = MutableLiveData<PerformerObject>()

    // The external immutable LiveData for the response String
    val performerObject: LiveData<PerformerObject>
        get() = _performerObject

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        updatePerformers()
    }

    // ------------- Albums ---------------
    private fun updatePerformers() {
        coroutineScope.launch {

            val randomDeferred = RandomApi.retrofitService.allPerformers()
            try {
                val result = randomDeferred.await()
                _performerObject.value = result
            } catch (e: Exception) {
                Log.e("PerformerListViewModel", "Failure: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}