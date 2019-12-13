package com.stankarp0.albumratings.ui.ratingdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stankarp0.albumratings.services.AlbumProperty
import com.stankarp0.albumratings.services.PerformerApi
import com.stankarp0.albumratings.services.PerformerProperty
import com.stankarp0.albumratings.services.RatingProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class RatingDetailsViewModel : ViewModel() {

    private val _performer = MutableLiveData<PerformerProperty>()
    val performer: LiveData<PerformerProperty>
        get() = _performer

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun findPerformer(rating: RatingProperty) {
        coroutineScope.launch {
            val performerDeferred = PerformerApi.retrofitService.performer(rating.performerId)
            try {
                val result = performerDeferred.await()
                _performer.value = result
            } catch (e: Exception) {
                Log.e("RatingDetailsViewModel","Failure: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}