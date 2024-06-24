package com.kbyai.facerecognition

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val identifyedFace = intent.getParcelableExtra("identified_face") as? Bitmap
        val enrolledFace = intent.getParcelableExtra("enrolled_face") as? Bitmap
        val identifiedId = intent.getStringExtra("identified_id")
        val identifiedName = intent.getStringExtra("identified_name")
        val identifiedPhone = intent.getStringExtra("identified_phone")

        findViewById<ImageView>(R.id.imageEnrolled).setImageBitmap(enrolledFace)
        findViewById<ImageView>(R.id.imageIdentified).setImageBitmap(identifyedFace)
        findViewById<TextView>(R.id.idText).text = "MSHS: " + identifiedId;
        findViewById<TextView>(R.id.nameText).text = "Họ và tên: " + identifiedName
        findViewById<TextView>(R.id.phoneText).text = "SĐT phụ huynh: " + identifiedPhone

        Handler().postDelayed({
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}