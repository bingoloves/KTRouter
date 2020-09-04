package com.app.crash.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.app.crash.util.NotificationUtil

/**
 * do nothing, just keep app run
 */
class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, NotificationUtil.createNotification(this))
        return super.onStartCommand(intent, flags, startId)
    }
}