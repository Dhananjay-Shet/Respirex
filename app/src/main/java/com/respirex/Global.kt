package com.respirex

import android.app.Application
import android.media.MediaPlayer
import com.respirex.data.ReportRepository

class Global : Application() {

    companion object {
        lateinit var instance: Global private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ReportRepository.fetchReport()
    }

    private var mediaPlayer : MediaPlayer?=null

    fun startAudio(audioCompleted:()->Unit) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.respirex).apply {
                setOnCompletionListener {
                    audioCompleted()
                }
            }
        }
        mediaPlayer?.start()
    }

    fun pauseAudio() {
        mediaPlayer?.pause()
    }

    fun stopAudio() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

}