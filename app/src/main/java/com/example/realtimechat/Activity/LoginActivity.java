package com.example.realtimechat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.realtimechat.MainActivity;
import com.example.realtimechat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText edtaccountLG;
    private EditText edtPassWordLG;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    private long backPressTime;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtaccountLG = findViewById(R.id.edtaccountLG);
        edtPassWordLG = findViewById(R.id.edtPassWordLG);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onClickForgotPass(View view) {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }

    public void onClickLogin(View view) {
        String txt_email = edtaccountLG.getText().toString();
        String txt_password = edtPassWordLG.getText().toString();
        if (!validateemail() | !validatepass()) {
            return;
        } else {
            firebaseAuth.signInWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void nextRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            mToast.cancel();

            Intent intent = new Intent(getApplicationContext(), WaitingScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            finish();
            System.exit(0);

        } else {
            mToast = Toast.makeText(LoginActivity.this, "Ấn lần nữa để thoát", Toast.LENGTH_SHORT);
            mToast.show();
        }
        backPressTime = System.currentTimeMillis();
    }

    public boolean validateemail() {
        String a = "^[a-zA-Z0-9]*@{1}gmail.com$";
        if (edtaccountLG.getText().toString().trim().equals("")) {
            edtaccountLG.setError("Hãy nhập gmail của bạn.");
            return false;
        } else if (!edtaccountLG.getText().toString().trim().matches(a)) {
            edtaccountLG.setError("Nhập đúng định dạng gmail.");
            return false;
        } else {
            edtaccountLG.setError(null);
            return true;
        }
    }

    public boolean validatepass() {
        if (edtPassWordLG.getText().toString().trim().equals("")) {
            edtPassWordLG.setError("Nhập mật khẩu của bạn");
            return false;
        } else {
            edtPassWordLG.setError(null);
            return true;
        }
    }


}