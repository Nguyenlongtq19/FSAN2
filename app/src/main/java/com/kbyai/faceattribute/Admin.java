package com.kbyai.faceattribute;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Admin extends AppCompatActivity {

    private Handler autoLogoutHandler;
    private Runnable autoLogoutRunnable;
    private static final int AUTO_LOGOUT_DELAY = 60000;
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
                Intent intent = new Intent(Admin.this, CaptureActivity.class);
                startActivity(intent);
                finish(); // Kết thúc activity hiện tại
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
}