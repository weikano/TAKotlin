package com.kingsunedu.takotlin.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kingsunedu.takotlin.utils.Event
import javax.inject.Inject

class LaunchViewModel @Inject constructor():ViewModel(){
    val launchDestination : LiveData<Event<LaunchDestination>>
    init {
        launchDestination = MutableLiveData<Event<LaunchDestination>>()
        launchDestination.postValue(Event(LaunchDestination.MAIN_ACTIVITY))
    }
}

enum class LaunchDestination {
    MAIN_ACTIVITY,
    LOGIN
}