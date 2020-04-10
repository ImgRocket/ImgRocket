package cn.imgrocket.imgrocket

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat

class EggListener : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("test", "test")
        val activityIntent = Intent(context, EggActivity::class.java)
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(activityIntent)
    }
}