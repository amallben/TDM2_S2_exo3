package com.example.adhan


import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import com.example.adhan.App.Companion.CHANNEL_ID
import java.util.*


class MyService : Service() {
    //creating a mediaplayer object
    private var alarmHour: Int? = null
    private var alarmMinute: Int? = null
    private var player: MediaPlayer? = null
    private val t: Timer = Timer()
    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        alarmHour = intent!!.getIntExtra("alarmHour", 0);
        alarmMinute = intent!!.getIntExtra("alarmMinute", 0);
        val input = intent!!.getStringExtra("inputExtra")
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_access)
            .setContentTitle("تطبيق الصلاة")
            .setContentText(input)
            .setContentIntent(pendingIntent)
            .build()

        //getting systems default ringtone
        player = MediaPlayer.create(
            this,
            R.raw.adhan
        )
////        //setting loop play to true
////        //this will make the ringtone continuously playing
        player!!.isLooping = true

        t.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (Calendar.getInstance().time.hours === alarmHour &&
                    Calendar.getInstance().time.minutes === alarmMinute
                ) {
                    startForeground(1, notification)
                    ////        //staring the player
                    player!!.start()
                } else {
                    //ringtone.stop()
                }
            }
        }, 0, 2000)




        //we have some options for service
        //start sticky means service will be explicity started and stopped
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        //stopping the player when service is destroyed
        player!!.stop()
    }
}