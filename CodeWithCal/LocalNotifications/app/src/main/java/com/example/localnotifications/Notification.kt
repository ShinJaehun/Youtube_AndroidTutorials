package com.example.localnotifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService

//const val notificationID = 1
//const val channelID = "channel1"
//const val titleExtra = "titleExtra"
//const val messageExtra = "messageExtra"

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class Notification : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent)
    {
        // 결국 이런게 문제가 아니구요.
        // 에뮬레이터에서 앱의 notification을 허용해주세요.
            val notification = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra(titleExtra))
                .setContentText(intent.getStringExtra(messageExtra))

//            val pendingIntent = PendingIntent.getActivity(context, 0, Intent(), 0)

            val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//            notification.setContentIntent(pendingIntent)

            manager.notify(notificationID, notification.build())

            Log.d("Notification", "(onReceive)");

        // 결과 동일
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notification = NotificationCompat.Builder(context, channelID)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentTitle(intent.getStringExtra(titleExtra))
//                .setContentText(intent.getStringExtra(messageExtra))
//                .build()
//
//            val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            manager.notify(notificationID, notification)
//
//            Log.d("Notification", "(onReceive) channelID : " + notification.channelId);
//
//        }

//        val notification = NotificationCompat.Builder(context, channelID)
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle(intent.getStringExtra(titleExtra))
//            .setContentText(intent.getStringExtra(messageExtra))
//            .build()
//
//        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.notify(notificationID, notification)

        // 결과 동일
//        with(manager) {
//            notify(notificationID, notification)
//        }

    }

}
