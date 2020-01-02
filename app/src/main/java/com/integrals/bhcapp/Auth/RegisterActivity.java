package com.integrals.bhcapp.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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
    Button FinishBtn;
    DatabaseReference Ref;
    LinearLayout RootRegActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RootRegActivity = findViewById(R.id.root_reg_view);
        NameET = findViewById(R.id.reg_name_field);
        ClassET = findViewById(R.id.reg_class_field);
        SchoolET = findViewById(R.id.reg_school_field);
        FinishBtn = findViewById(R.id.reg_btn);
        Ref = FirebaseDatabase.getInstance().getReference();

        FinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(NameET.getText().toString()) && !TextUtils.isEmpty(ClassET.getText().toString()) && !TextUtils.isEmpty(SchoolET.getText().toString()))
                {
                    String uname = NameET.getText().toString();
                    String uclass = ClassET.getText().toString();
                    String uschool = SchoolET.getText().toString();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    Map userdata = new HashMap();
                    userdata.put("name",uname);
                    userdata.put("class",uclass);
                    userdata.put("school",uschool);

                    Ref.child("Users").child(uid).setValue(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
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
