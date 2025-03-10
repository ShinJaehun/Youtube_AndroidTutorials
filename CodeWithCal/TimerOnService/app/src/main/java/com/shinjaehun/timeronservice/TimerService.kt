package com.shinjaehun.timeronservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.*

class TimerService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    private val timer = Timer()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val time = intent.getDoubleExtra(TIME_EXTRA, 0.0)
        timer.scheduleAtFixedRate(TimeTask(time), 0, 1000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    private inner class TimeTask(private var time: Double) : TimerTask() {
        override fun run() {
            val intent = Intent(TIMER_UPDATED)
            time++
            intent.putExtra(TIME_EXTRA, time)
            sendBroadcast(intent)

            Log.d("TimerService", "TIMER_UPDATED " + intent.getDoubleExtra(TimerService.TIMER_UPDATED, 0.0).toString())
            Log.d("TimerService", "TIME_EXTRA " + intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0).toString())

        }
    }

    companion object {
        const val TIMER_UPDATED = "timerUpdated"
        const val TIME_EXTRA = "timeExtra"
    }
}