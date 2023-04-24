package com.example.coachmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "channel_id"
    private var notificationId = 0
    private val COACH_ID = 1
    private val userRemoteDataSource = UserRemoteDataSource()
    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        scheduler.scheduleAtFixedRate({
            userRemoteDataSource.findById(COACH_ID) { user ->
                if(user != null) {
                    Log.i(">>>", user.toString())
                    sendNotification("Alert", user.toString())
                } else {
                    Log.e(">>>", "user NULL")
                }
            }
        }, 0, 10, TimeUnit.SECONDS)

        val button: Button = findViewById(R.id.btnStopScheduler)
        button.setOnClickListener {
            Log.i(">>>", "Stop scheduler")
            scheduler.shutdown()
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(title: String, content: String) {
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
            notificationId++
        }
    }
}