package com.example.notificationfromtopic

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, 1)
            }
        }
        val topic_subscribe = findViewById<TextInputLayout>(R.id.topic_subscribe)
        val topic_toSubscribe = findViewById<TextInputEditText>(R.id.topic_toSubscribe)
        val topic_unsubscribe = findViewById<TextInputLayout>(R.id.topic_unsubscribe)
        val topic_toUnsubscribe = findViewById<TextInputEditText>(R.id.topic_toUnsubscribe)
        val send_btn = findViewById<MaterialButton>(R.id.send_btn)
        val title_edt = findViewById<TextInputEditText>(R.id.title_edt)
        val content_edt = findViewById<TextInputEditText>(R.id.content_edt)
        val topic_toSend = findViewById<TextInputEditText>(R.id.topic_toSend)



        topic_subscribe.setEndIconOnClickListener {
            if (!topic_toSubscribe.text.toString().isNullOrEmpty()) {
                MyFirebaseMessagingService.subscribeTopic(
                    this@MainActivity,
                    topic_toSubscribe.text.toString()
                )
            } else {
                topic_toSubscribe.error = "Fill this field"
            }
        }

        topic_unsubscribe.setEndIconOnClickListener {
            if (!topic_toUnsubscribe.text.toString().isNullOrEmpty()) {
                MyFirebaseMessagingService.unsubscribeTopic(
                    this@MainActivity,
                    topic_toUnsubscribe.text.toString()
                )
            } else {
                topic_toUnsubscribe.error = "Fill this field"
            }
        }

        send_btn.setOnClickListener {
            when {
                title_edt.text.toString().isEmpty() -> title_edt.error = "Fill this field"
                content_edt.text.toString().isEmpty() -> content_edt.error = "Fill this field"
                else -> {
                    MyFirebaseMessagingService.sendMessage(
                        title_edt.text.toString(),
                        content_edt.text.toString(),
                        topic_toSend.text.toString()
                    )

                }
            }
        }
    }
}