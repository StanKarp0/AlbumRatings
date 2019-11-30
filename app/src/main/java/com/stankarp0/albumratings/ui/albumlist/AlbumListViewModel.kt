package com.stankarp0.albumratings.ui.albumlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stankarp0.albumratings.services.AlbumApi
import com.stankarp0.albumratings.services.AlbumObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AlbumListViewModel : ViewModel() {

    private val _albumObject = MutableLiveData<AlbumObject>()

    val albumObject: LiveData<AlbumObject>
        get() = _albumObject

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        updateAlbums()
    }

    // ------------- Albums ---------------
    private fun updateAlbums() {
        coroutineScope.launch {
            val randomDeferred = AlbumApi.retrofitService.all()
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