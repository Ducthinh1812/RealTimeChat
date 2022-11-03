package com.example.realtimechat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtHoTenRG;
    private EditText edtEmailRG;
    private EditText edtPassWordRG;
    private EditText edtRePassWordRG;

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        edtHoTenRG = findViewById(R.id.edtHoTenRG);
        edtEmailRG = findViewById(R.id.edtEmailRG);
        edtPassWordRG = findViewById(R.id.edtPassWordRG);
        edtRePassWordRG = findViewById(R.id.edtRePassWordRG);
    }

    public void onClickRegister(View view) {
        String txt_username = edtHoTenRG.getText().toString();
        String txt_email = edtEmailRG.getText().toString();
        String txt_password = edtPassWordRG.getText().toString();
        if (!validatename() | !validaterepass() | !validateemail() | !validatepass()) {
            return;
        } else {
            firebaseAuth.createUserWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        assert firebaseUser != null;
                        String user = firebaseUser.getUid();
                        reference = FirebaseDatabase.getInstance().getReference("Users").child(user);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", user);
                        hashMap.put("username", txt_username);
                        hashMap.put("imageURL", "default");
                        hashMap.put("status", "offline");
                        hashMap.put("search", txt_username.toLowerCase());
                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "You can't register woth this email or password", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    public void BackToLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public boolean validatename() {
        if (edtHoTenRG.getText().toString().trim().equals("")) {
            edtHoTenRG.setError("Hãy nhập tên của bạn.");
            return false;
        } else {
            edtHoTenRG.setError(null);
            return true;
        }
    }

    public boolean validateemail() {
        String a = "^[a-zA-Z0-9]*@{1}gmail.com$";
        if (edtEmailRG.getText().toString().trim().equals("")) {
            edtEmailRG.setError("Hãy nhập gmail của bạn.");
            return false;
        } else if (!edtEmailRG.getText().toString().trim().matches(a)) {
            edtEmailRG.setError("Nhập đúng định dạng gmail.");
            return false;
        } else {
            edtEmailRG.setError(null);
            return true;
        }
    }

    public boolean validatepass() {
        if (edtPassWordRG.getText().toString().trim().equals("")) {
            edtPassWordRG.setError("Nhập mật khẩu của bạn");
            return false;
        } else if (edtPassWordRG.length() < 6) {
            edtPassWordRG.setError("Nhập mật khẩu trên 6 kí tự.");
            return false;
        } else {
            edtPassWordRG.setError(null);
            return true;
        }
    }

    public boolean validaterepass() {
        if (!edtRePassWordRG.getText().toString().trim().equals(edtPassWordRG.getText().toString().trim())) {
            edtRePassWordRG.setError("Mật khẩu nhập lại không đúng");
            return false;
        } else {
            edtRePassWordRG.setError(null);
            return true;
        }
    }
}