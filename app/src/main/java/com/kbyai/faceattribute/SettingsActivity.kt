package com.kbyai.faceattribute

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.preference.*


class SettingsActivity : AppCompatActivity() {

    companion object {
        const val DEFAULT_CAMERA_LENS = "front"
        const val DEFAULT_LIVENESS_THRESHOLD = "0.5"
        const val DEFAULT_IDENTIFY_THRESHOLD = "0.6"
        const val DEFAULT_LIVENESS_LEVEL = "0"
        const val DEFAULT_YAW_THRESHOLD = "10.0"
        const val DEFAULT_ROLL_THRESHOLD = "10.0"
        const val DEFAULT_PITCH_THRESHOLD = "10.0"
        const val DEFAULT_OCCLUSION_THRESHOLD = "0.5"
        const val DEFAULT_EYECLOSE_THRESHOLD = "0.5"
        const val DEFAULT_MOUTHOPEN_THRESHOLD = "0.5"

        @JvmStatic
        fun getLivenessThreshold(context: Context): Float {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getString("liveness_threshold", SettingsActivity.DEFAULT_LIVENESS_THRESHOLD)!!.toFloat()
        }

        @JvmStatic
        fun getIdentifyThreshold(context: Context): Float {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getString("identify_threshold", SettingsActivity.DEFAULT_IDENTIFY_THRESHOLD)!!.toFloat()
        }

        @JvmStatic
        fun getCameraLens(context: Context): Int {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val cameraLens = sharedPreferences.getString("camera_lens", SettingsActivity.DEFAULT_CAMERA_LENS)
            if(cameraLens == "back") {
                return CameraSelector.LENS_FACING_BACK
            } else {
                return CameraSelector.LENS_FACING_FRONT
            }
        }

        @JvmStatic
        fun getLivenessLevel(context: Context): Int {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val livenessLevel = sharedPreferences.getString("liveness_level", SettingsActivity.DEFAULT_LIVENESS_LEVEL)
            if(livenessLevel == "0") {
                return 0
            } else {
                return 1
            }
        }

        @JvmStatic
        fun getYawThreshold(context: Context): Float {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getString("yaw_threshold", SettingsActivity.DEFAULT_YAW_THRESHOLD)!!.toFloat()
        }

        @JvmStatic
        fun getRollThreshold(context: Context): Float {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getString("roll_threshold", SettingsActivity.DEFAULT_ROLL_THRESHOLD)!!.toFloat()
        }

        @JvmStatic
        fun getPitchThreshold(context: Context): Float {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getString("pitch_threshold", SettingsActivity.DEFAULT_PITCH_THRESHOLD)!!.toFloat()
        }

        @JvmStatic
        fun getOcclusionThreshold(context: Context): Float {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getString("occlusion_threshold", SettingsActivity.DEFAULT_OCCLUSION_THRESHOLD)!!.toFloat()
        }

        @JvmStatic
        fun getEyecloseThreshold(context: Context): Float {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getString("eyeclose_threshold", SettingsActivity.DEFAULT_EYECLOSE_THRESHOLD)!!.toFloat()
        }

        @JvmStatic
        fun getMouthopenThreshold(context: Context): Float {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getString("mouthopen_threshold", SettingsActivity.DEFAULT_MOUTHOPEN_THRESHOLD)!!.toFloat()
        }
    }

    lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbManager = DBManager(this)
    }
}