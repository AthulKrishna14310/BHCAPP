package com.integrals.bhcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.integrals.bhcapp.Auth.LoginActivity;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth UserAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserAuth = FirebaseAuth.getInstance();

        CheckUserAuth();

    }

    private void CheckUserAuth() {

        if(UserAuth.getCurrentUser() == null)
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }
}
