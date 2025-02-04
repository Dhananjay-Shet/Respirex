package com.respirex.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.respirex.Global
import com.respirex.data.Firebase
import com.respirex.data.ReportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewmodel() : ViewModel() {

    fun getCurrentUser(): FirebaseUser? {
        return Firebase.getCurrentUser()
    }

    fun logoutUser(activityContext: Context, onLogoutComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            Firebase.logoutUser(activityContext) {
                onLogoutComplete()
                ReportRepository.setValueToInitial()
            }
        }
    }

    private val _isPlaying = MutableLiveData(false)
    val isPlaying : LiveData<Boolean> get() = _isPlaying

    fun toggle(){
        _isPlaying.value=!isPlaying.value!!
    }

    fun startAudio(){
        Global.instance.startAudio{
            toggle()
        }
    }

//    fun pauseAudio(){
//        Global.instance.pauseAudio()
//    }

    fun stopAudio(){
        if (isPlaying.value == true){
            Global.instance.stopAudio()
            toggle()
        }
    }
}