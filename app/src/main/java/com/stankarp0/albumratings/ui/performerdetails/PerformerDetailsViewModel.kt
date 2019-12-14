package com.stankarp0.albumratings.ui.performerdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stankarp0.albumratings.services.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PerformerDetailsViewModel : ViewModel() {

    private val _albumObject = MutableLiveData<AlbumObject>()
    private val _performer = MutableLiveData<PerformerProperty>()

    val albumObject: LiveData<AlbumObject>
        get() = _albumObject

    val performer: LiveData<PerformerProperty>
        get() = _performer

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    // ------------- Albums ---------------
    fun updateModel(performer: PerformerProperty) {
        _performer.value = performer
        updateAlbums(performer.performerId)
    }

    private fun updateAlbums(performerId: Int) {
        coroutineScope.launch {
            val randomDeferred = RandomApi.retrofitService.performerAlbums(performerId)
            try {
                val result = randomDeferred.await()
                _albumObject.value = result
            } catch (e: Exception) {
                Log.e("PerformerDetailsViewMod", "Failure: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}