package com.example.realtimechat.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realtimechat.Model.User;
import com.example.realtimechat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText send_email;
    Button btn_reset;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        send_email = findViewById(R.id.send_email);
        btn_reset = findViewById(R.id.btn_reset);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = send_email.getText().toString().trim();

                if (!validateemail()) {
                    return;
                } else {
                    Sendemail(email);
                }
            }
        });

    }

    private void Sendemail(String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
        builder.setTitle("X??c nh???n t??i kho???n.");
        builder.setMessage("Ch??ng t??i s??? g???i cho b???n m?? ????? x??c nh???n t??i kho???n n??y l?? c???a b???n.");
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Please check you Email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(ForgotPasswordActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public boolean validateemail() {
        String a = "^[a-zA-Z0-9]*@{1}gmail.com$";
        if (send_email.getText().toString().trim().equals("")) {
            send_email.setError("H??y nh???p gmail c???a b???n.");
            return false;
        } else if (!send_email.getText().toString().trim().matches(a)) {
            send_email.setError("Nh???p ????ng ?????nh d???ng gmail.");
            return false;
        } else {
            send_email.setError(null);
            return true;
        }
    }

}