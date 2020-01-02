package com.integrals.bhcapp.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.integrals.bhcapp.Helper.SnackbarMessage;
import com.integrals.bhcapp.MainActivity;
import com.integrals.bhcapp.R;

public class LoginActivity extends AppCompatActivity {

    EditText EmailField,PasswordField;
    Button LoginBtn;
    TextView ForgotPasswordTV;
    RelativeLayout RootAuthLayout;
    Dialog PasswordRecovery;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailField = findViewById(R.id.auth_email_field);
        PasswordField = findViewById(R.id.auth_password_field);
        LoginBtn = findViewById(R.id.auth_login_btn);
        ForgotPasswordTV = findViewById(R.id.auth_forgot_password_tv);
        RootAuthLayout= findViewById(R.id.auth_root_view);
        PasswordRecovery = new Dialog(this);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(manager.isAcceptingText())
                {
                    manager.hideSoftInputFromWindow(EmailField.getWindowToken(),0);
                }

                if(!TextUtils.isEmpty(EmailField.getText().toString()) && !TextUtils.isEmpty(PasswordField.getText().toString()) )
                {
                    String email = EmailField.getText().toString();
                    String password =  PasswordField.getText().toString();
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                            else
                            {
                                new SnackbarMessage().ShowSnackbarMessage(RootAuthLayout,task.getException().toString(),false);
                            }

                        }
                    });
                }
                else
                {
                    new SnackbarMessage().ShowSnackbarMessage(RootAuthLayout,"Complete the above fields.",false);
                }

            }
        });


        PasswordRecovery.setContentView(R.layout.forgotpassword_layout);
        PasswordRecovery.setCanceledOnTouchOutside(false);
        PasswordRecovery.setCancelable(false);

        final EditText RecoveryMailET = PasswordRecovery.findViewById(R.id.forgotpassword_ET);
        Button CancelRecoveryBtn = PasswordRecovery.findViewById(R.id.forgotpassword_cancel_btn);
        Button ResetRecoveryBtn = PasswordRecovery.findViewById(R.id.forgotpassword_reset_btn);

        CancelRecoveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PasswordRecovery.dismiss();

            }
        });

        ResetRecoveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(manager.isAcceptingText())
                {
                    manager.hideSoftInputFromWindow(RecoveryMailET.getWindowToken(),0);
                }

                if(!TextUtils.isEmpty(RecoveryMailET.getText().toString()))
                {
                    String recoverymail = RecoveryMailET.getText().toString();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(recoverymail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            if(task.isSuccessful())
                            {
                                PasswordRecovery.dismiss();
                                new SnackbarMessage().ShowSnackbarMessage(RootAuthLayout,"Password recovery mail has been sent.",true);
                            }
                            else
                            {

                                new SnackbarMessage().ShowSnackbarMessage(RootAuthLayout,"Unable to send password recovery mail. Try again.",true);

                            }

                        }
                    });
                }
                else
                {
                    new SnackbarMessage().ShowSnackbarMessage(RootAuthLayout,"Complete the email field for recovery.",true);
                }


            }
        });

        ForgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PasswordRecovery.show();

            }
        });

    }
}
