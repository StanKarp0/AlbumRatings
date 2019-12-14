package com.stankarp0.albumratings.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stankarp0.albumratings.services.*
import kotlinx.coroutines.*


class MainViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _albumObject = MutableLiveData<AlbumObject>()

    // The external immutable LiveData for the response String
    val albumObject: LiveData<AlbumObject>
        get() = _albumObject

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        updateRandomAlbums()
    }

    // ------------- Albums ---------------
    private fun updateRandomAlbums() {
        coroutineScope.launch {
            val randomDeferred = RandomApi.retrofitService.albumsQuery("Lennon")

            try {
                val result = randomDeferred.await()
                _albumObject.value = result
            } catch (e: Exception) {
                Log.e("MainViewModel", "Failure: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
