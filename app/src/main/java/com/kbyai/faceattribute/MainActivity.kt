package com.kbyai.faceattribute

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.kbyai.faceattribute.SettingsActivity
import com.kbyai.facesdk.FaceBox
import com.kbyai.facesdk.FaceDetectionParam
import com.kbyai.facesdk.FaceSDK
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    companion object {
        private val SELECT_PHOTO_REQUEST_CODE = 1
        private val SELECT_ATTRIBUTE_REQUEST_CODE = 2
    }

    private lateinit var dbManager: DBManager
    private lateinit var textWarning: TextView
    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textWarning = findViewById<TextView>(R.id.textWarning)


        var ret = FaceSDK.setActivation(
            "fGMbqRWAN9PrnQBHd3JtdbNCKJ75REHRN4yenuntm9SghMVrQztH8IQIObnN3hJc6RitR139CwnP\n" +
                    "P/hUVlINXCk48PkGrTJlNsFUm5ErOXL2QWw7IUzQow/DALUwvKOR4Qpz3i0lHKVlrFqMOKb4y3DH\n" +
                    "Dhb/Fh6KLywr5aWy5Lwv/hutFqe6gao9xVqpbOLq2yP+OIjPpW0teMxEjSKGhuQftp7lV9tEnv9B\n" +
                    "lAI75/ElCUYb6vxWCqZFSGLLiDuEyTbz7Npz1rhuQkwmotgLTYrij0zzIt79TccUve9lx2xl/fqS\n" +
                    "y6YUynuO4VN/awOJQFMv4HpFVFVupmU/ezM7Tg=="
        )

        if (ret == FaceSDK.SDK_SUCCESS) {
            ret = FaceSDK.init(assets)
        }

        if (ret != FaceSDK.SDK_SUCCESS) {
            textWarning.setVisibility(View.VISIBLE)
            if (ret == FaceSDK.SDK_LICENSE_KEY_ERROR) {
                textWarning.setText("Invalid license!")
            } else if (ret == FaceSDK.SDK_LICENSE_APPID_ERROR) {
                textWarning.setText("Invalid error!")
            } else if (ret == FaceSDK.SDK_LICENSE_EXPIRED) {
                textWarning.setText("License expired!")
            } else if (ret == FaceSDK.SDK_NO_ACTIVATED) {
                textWarning.setText("No activated!")
            } else if (ret == FaceSDK.SDK_INIT_ERROR) {
                textWarning.setText("Init error!")
            }
        }

        dbManager = DBManager(this)
        dbManager.loadPerson()

        personAdapter = PersonAdapter(this, DBManager.personList)
        val listView: ListView = findViewById<View>(R.id.listPerson) as ListView
        listView.setAdapter(personAdapter)

        findViewById<Button>(R.id.buttonEnroll).setOnClickListener {
            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_PICK)
            startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), SELECT_PHOTO_REQUEST_CODE)
        }

        findViewById<Button>(R.id.buttonIdentify).setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }

        findViewById<Button>(R.id.buttonCapture).setOnClickListener {
            startActivity(Intent(this, CaptureActivity::class.java))
        }

        findViewById<Button>(R.id.buttonAttribute).setOnClickListener {
            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_PICK)
            startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), SELECT_ATTRIBUTE_REQUEST_CODE)
        }

        findViewById<Button>(R.id.buttonSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.lytBrand).setOnClickListener {
            val browse = Intent(Intent.ACTION_VIEW, Uri.parse("https://kby-ai.com"))
            startActivity(browse)
        }
    }

    override fun onResume() {
        super.onResume()

        personAdapter.notifyDataSetChanged()
    }
}