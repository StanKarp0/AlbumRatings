package com.stankarp0.albumratings.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stankarp0.albumratings.services.AlbumObject
import com.stankarp0.albumratings.services.RandomApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()
    private val _properties = MutableLiveData<AlbumObject>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    val properties: LiveData<AlbumObject>
        get() = _properties

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call updateRandomAlbums() on init so we can display status immediately.
     */
    init {
        updateRandomAlbums()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun updateRandomAlbums() {
        coroutineScope.launch {
            val randomDeferred = RandomApi.retrofitService.random()

            try {
                val result = randomDeferred.await()
                _response.value ="Success: ${result.albums.size} Mars properties retrieved"
                _properties.value = result
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
            Log.i("MainViewModel", _response.value.toString())
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
