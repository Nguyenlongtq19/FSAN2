package com.kbyai.facerecognition;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.kbyai.facesdk.FaceBox;
import com.kbyai.facesdk.FaceDetectionParam;
import com.kbyai.facesdk.FaceSDK;
import java.util.List;
import kotlin.random.Random;

public class Admin extends AppCompatActivity {

    private static final int SELECT_PHOTO_REQUEST_CODE = 1;
    private Handler autoLogoutHandler;
    private Runnable autoLogoutRunnable;
    private static final int AUTO_LOGOUT_DELAY = 60000;

    private DBManager dbManager;
    private TextView textWarning;
    private PersonAdapter personAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        pressBtnHome();
        addMember();
        settings();
        listPerson();
        autoLogoutHandler = new Handler(Looper.getMainLooper());
        autoLogoutRunnable = new Runnable() {
            @Override
            public void run() {
                // Auto logout after 10 seconds of inactivity
                Intent intent = new Intent(Admin.this, MainActivity2.class);
                startActivity(intent);
                finish(); // Finish the current activity
            }
        };
        autoLogoutHandler.removeCallbacks(autoLogoutRunnable);
        startAutoLogoutTimer();

    }

    private void addMember() {
        Button addMemberButton = (Button) findViewById(R.id.addMemberButton);
        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), SELECT_PHOTO_REQUEST_CODE);
            }
        });
    }

    private void listPerson() {
        Button listPersonButton = (Button) findViewById(R.id.listPersonButton);
        listPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, ListPerson.class);
                startActivity(intent);
                finish(); // Kết thúc activity hiện tại
            }
        });
    }

    private void settings() {
        Button settingButton = (Button) findViewById(R.id.settingsButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, SettingsActivity.class);
                startActivity(intent);
                finish(); // Kết thúc activity hiện tại
            }
        });
    }

    private void pressBtnHome() {
        Button btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, MainActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish(); // Kết thúc activity hiện tại
            }
        });
    }

    public void onBackPressed() {
        // Chuyển người dùng về Activity chính
        Intent intent = new Intent(Admin.this, MainActivity2.class);
        startActivity(intent);
        finish(); // Kết thúc activity hiện tại
    }

    private void startAutoLogoutTimer() {
        autoLogoutHandler.postDelayed(autoLogoutRunnable, AUTO_LOGOUT_DELAY);
    }
    protected void onDestroy() {
        super.onDestroy();
        // Remove any pending auto-logout callbacks to avoid leaks
        autoLogoutHandler.removeCallbacks(autoLogoutRunnable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = Utils.getCorrectlyOrientedImage(this, data.getData());

                FaceDetectionParam faceDetectionParam = new FaceDetectionParam();
                faceDetectionParam.check_liveness = true;
                faceDetectionParam.check_liveness_level = SettingsActivity.getLivenessLevel(this);
                List<FaceBox> faceBoxes = FaceSDK.faceDetection(bitmap, faceDetectionParam);

                if (faceBoxes == null || faceBoxes.isEmpty()) {
                    Toast.makeText(this, getString(R.string.no_face_detected), Toast.LENGTH_SHORT).show();
                } else if (faceBoxes.size() > 1) {
                    Toast.makeText(this, getString(R.string.multiple_face_detected), Toast.LENGTH_SHORT).show();
                } else {
                    Bitmap faceImage = Utils.cropFace(bitmap, faceBoxes.get(0));
                    byte[] templates = FaceSDK.templateExtraction(bitmap, faceBoxes.get(0));

                    dbManager.insertPerson("id","Name","Phone", faceImage, templates);
                    personAdapter.notifyDataSetChanged();
                    Toast.makeText(this, getString(R.string.person_enrolled), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}