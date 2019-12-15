package com.stankarp0.albumratings.ui.albumlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stankarp0.albumratings.services.AlbumEmbedded
import com.stankarp0.albumratings.services.AlbumObject
import com.stankarp0.albumratings.services.RandomApi
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
    private var page = 0
    private var query = ""

    init {
        updateAlbums()
    }

    // ------------- Albums ---------------
    fun updateAlbums() {
        coroutineScope.launch {
            val randomDeferred = RandomApi.retrofitService.albumsQuery(query, page)
            page += 1

            try {
                val result = randomDeferred.await()
                if (_albumObject.value == null) {
                    _albumObject.value = result
                } else if (result.albums.isNotEmpty()){
                    val albums = (_albumObject.value?.albums ?: listOf()) + result.albums
                    _albumObject.value = AlbumObject(AlbumEmbedded(albums))
                }
            } catch (e: Exception) {
                Log.e("PerformerDetailsViewMod", "Failure: ${e.message}")
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
        _albumObject.value = null
        updateAlbums()
    }

    fun reloadAlbums() {
        page = 0
        _albumObject.value = null
        updateAlbums()
    }
}