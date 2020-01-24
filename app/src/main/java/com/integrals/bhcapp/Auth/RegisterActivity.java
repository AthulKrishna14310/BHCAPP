package com.integrals.bhcapp.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.integrals.bhcapp.Helper.SnackbarMessage;
import com.integrals.bhcapp.MainActivity;
import com.integrals.bhcapp.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText NameET,ClassET,SchoolET;
    TextView FinishBtn;
    DatabaseReference Ref,Ref2;
    LinearLayout RootRegActivity;
    String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_2);
        getSupportActionBar().hide();
        RootRegActivity = findViewById(R.id.root_reg_view);
        NameET = findViewById(R.id.reg_name_field);
        ClassET = findViewById(R.id.reg_class_field);
        SchoolET = findViewById(R.id.reg_school_field);
        FinishBtn = findViewById(R.id.reg_btn);
        Ref = FirebaseDatabase.getInstance().getReference();
        Ref2 = FirebaseDatabase.getInstance().getReference();
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");


        FinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(NameET.getText().toString()) && !TextUtils.isEmpty(ClassET.getText().toString()) && !TextUtils.isEmpty(SchoolET.getText().toString()))
                {

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                String uname = NameET.getText().toString();
                                String uclass = ClassET.getText().toString();
                                String uschool = SchoolET.getText().toString();
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                final Map userdata = new HashMap();
                                userdata.put("email",email);
                                userdata.put("name",uname);
                                userdata.put("class",uclass);
                                userdata.put("school",uschool);


                                Ref.child("Users").child(email.replace(".","_")).setValue(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful())
                                        {
                                            Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                                            i.putExtra("email",email);
                                            i.putExtra("password",password);
                                            startActivity(i);
                                            finish();
                                        }
                                        else
                                        {
                                            ShowSnackbarMessage(task.getException().toString(),false);

                                        }

                                    }
                                });




                            }
                            else
                            {
                                new SnackbarMessage().ShowSnackbarMessage(RootRegActivity,"User already exist or please check the internet connection. ",false);
                            }
                        }
                    });




                }
                else
                {
                    ShowSnackbarMessage("Fill in all fields.",false);
                }

            }
        });


    }

    public void ShowSnackbarMessage(String message,boolean isDurationLong)
    {
        if(isDurationLong)
        {
            Snackbar.make(RootRegActivity,message,Snackbar.LENGTH_LONG).show();

        }
        else
        {
            Snackbar.make(RootRegActivity,message,Snackbar.LENGTH_SHORT).show();

        }
    }
}
