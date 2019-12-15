package com.stankarp0.albumratings.ui.performerlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stankarp0.albumratings.services.*
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
    private var page = 0
    private var query = ""

    init {
        updatePerformers()
    }

    // ------------- Albums ---------------
    fun updatePerformers() {
        coroutineScope.launch {
            val randomDeferred = RandomApi.retrofitService.performersQuery(query, page)
            page += 1

            try {
                val result = randomDeferred.await()
                if (_performerObject.value == null) {
                    _performerObject.value = result
                } else if (result.performers.isNotEmpty()){
                    val performers = (_performerObject.value?.performers ?: listOf()) + result.performers
                    _performerObject.value = PerformerObject(PerformerEmbedded(performers))
                }
            } catch (e: Exception) {
                Log.e("PerformerListViewModel", "Failure: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun setQuery(text: String) {
        page = 0
        query = text
        _performerObject.value = null
        updatePerformers()
    }

    fun reloadPerformers() {
        page = 0
        _performerObject.value = null
        updatePerformers()
    }

}