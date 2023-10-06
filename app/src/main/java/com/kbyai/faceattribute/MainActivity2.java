package com.kbyai.faceattribute;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kbyai.facesdk.FaceSDK;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    private TextView clockTextView;
    private Handler handler;
    private Button attendanceButton;
    private Button loginButton;
    private TextView dateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        int ret = FaceSDK.setActivation(
                "fGMbqRWAN9PrnQBHd3JtdbNCKJ75REHRN4yenuntm9SghMVrQztH8IQIObnN3hJc6RitR139CwnP\n" +
                        "P/hUVlINXCk48PkGrTJlNsFUm5ErOXL2QWw7IUzQow/DALUwvKOR4Qpz3i0lHKVlrFqMOKb4y3DH\n" +
                        "Dhb/Fh6KLywr5aWy5Lwv/hutFqe6gao9xVqpbOLq2yP+OIjPpW0teMxEjSKGhuQftp7lV9tEnv9B\n" +
                        "lAI75/ElCUYb6vxWCqZFSGLLiDuEyTbz7Npz1rhuQkwmotgLTYrij0zzIt79TccUve9lx2xl/fqS\n" +
                        "y6YUynuO4VN/awOJQFMv4HpFVFVupmU/ezM7Tg=="
        );

        if (ret == FaceSDK.SDK_SUCCESS) {
            ret = FaceSDK.init(getAssets());
        }

        clockTextView = findViewById(R.id.clockTextView);

        handler = new Handler(Looper.getMainLooper());

        // Tạo một luồng để cập nhật đồng hồ mỗi giây.
        Thread clockThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(1000); // Chờ 1 giây
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                updateClock();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        clockThread.start();

        attendanceButton = (Button) findViewById(R.id.attendanceButton);

        attendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, Attendance.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                finish();
            }
        });

        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, Login.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                finish();
            }
        });

        dateTextView = findViewById(R.id.dateTextView);

        // Lấy ngày, tháng và năm hiện tại
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        // Hiển thị ngày, tháng và năm trên TextView
        dateTextView.setText("" + currentDate);
    }

    private void updateClock() {
        // Lấy thời gian hiện tại và định dạng nó
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());

        // Cập nhật TextView với thời gian hiện tại
        clockTextView.setText(currentTime);
    }
    protected void onPause() {
        super.onPause();
        finish();
    }
}
