package com.stankarp0.albumratings.ui.ratinglist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stankarp0.albumratings.services.RandomApi
import com.stankarp0.albumratings.services.RatingObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RatingListViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _ratingObject = MutableLiveData<RatingObject>()

    // The external immutable LiveData for the response String
    val ratingObject: LiveData<RatingObject>
        get() = _ratingObject

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        updateRatings()
    }

    private fun updateRatings() {
        coroutineScope.launch {
            val randomDeferred = RandomApi.retrofitService.allRatings()
            try {
                val result = randomDeferred.await()
                _ratingObject.value = result
            } catch (e: Exception) {
                Log.e("RatingListViewModel","Failure: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}