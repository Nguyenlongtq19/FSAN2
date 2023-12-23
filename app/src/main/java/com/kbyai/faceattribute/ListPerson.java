package com.kbyai.faceattribute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;

public class ListPerson extends AppCompatActivity {
    private PersonAdapter  personAdapter;
    private DBManager dbManager;
    private ListView listPerson;

    private Handler autoLogoutHandler;
    private Runnable autoLogoutRunnable;
    private static final int AUTO_LOGOUT_DELAY = 60000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_person);

        dbManager = new DBManager(this);
        dbManager.loadPerson();

        personAdapter = new PersonAdapter(this, DBManager.personList);
        listPerson = (ListView)findViewById(R.id.listPerson);
        listPerson.setAdapter(personAdapter);

        autoLogoutHandler = new Handler(Looper.getMainLooper());
        autoLogoutRunnable = new Runnable() {
            @Override
            public void run() {
                // Auto logout after 10 seconds of inactivity
                Intent intent = new Intent(ListPerson.this, MainActivity2.class);
                startActivity(intent);
                finish(); // Finish the current activity
            }
        };
        autoLogoutHandler.removeCallbacks(autoLogoutRunnable);
        startAutoLogoutTimer();
    }
    public void onBackPressed() {
        // Chuyển người dùng về Activity chính
        Intent intent = new Intent(ListPerson.this, Admin.class);
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