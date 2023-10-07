package com.kbyai.faceattribute;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UnLogin extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView forgotTextView;
    private TextView errorTextView;

    private FirebaseAuth auth;

    private int loginAttempts = 0;
    private boolean loginLocked = false;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        forgotTextView = findViewById(R.id.forgotTextView);
        errorTextView = findViewById(R.id.errorTextView);

        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loginLocked) {
                    signIn();
                } else {
                    showToast("Tài khoản đã bị khóa đăng nhập. Vui lòng thử lại sau.");
                }
            }
        });

        forgotTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Đăng nhập bằng máy tính để lấy lại mật khẩu hoặc liên hệ nhà phát triển");
            }
        });
    }

    private void signIn() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            showToast("Vui lòng nhập email và mật khẩu.");
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Đăng nhập thành công, thực hiện hành động sau đăng nhập
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            // Đăng nhập thành công, điều hướng đến hoạt động chính hoặc trang khác
                            Intent intent = new Intent(UnLogin.this, Admin.class);
                            startActivity(intent);
                            finish(); // Đóng hoạt động đăng nhập nếu cần

                        }
                    } else {
                        // Đăng nhập thất bại
                        loginAttempts++;
                        if (loginAttempts >= 5) {
                            loginLocked = true;
                            startCountdownTimer();
                        } else {
                            showToast("Đăng nhập thất bại. Vui lòng thử lại.");
                        }
                    }
                });
    }
    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Cập nhật TextView để hiển thị đếm ngược
                errorTextView.setText("Đăng nhập sai quá 5 lần. \nVui lòng thử lại sau " +
                        millisUntilFinished / 1000 + " giây.");
            }

            @Override
            public void onFinish() {
                // Khi đếm ngược kết thúc, mở khóa lại đăng nhập
                loginAttempts = 0;
                loginLocked = false;
                errorTextView.setText("");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hủy bỏ đếm ngược khi hoạt động bị hủy
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
