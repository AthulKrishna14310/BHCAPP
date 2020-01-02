package com.integrals.bhcapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.integrals.bhcapp.Auth.LoginActivity;
import com.integrals.bhcapp.Helper.HomeCard;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth UserAuth;
    RecyclerView MainRV;
    List<HomeCard> ExerciseList;
    String packageNameKidzee="com.example.kidzeee";
    private String packageNamePattern="com.example.circletrace";
    private String packageNameConcentric="com.example.concentriccircledraw";
    private String packageNameLetter="com.example.letterpractice";
    private String packageNameGame="com.DefaultCompany.AntFrenzy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        UserAuth = FirebaseAuth.getInstance();
        MainRV = findViewById(R.id.main_RV);
        MainRV.setHasFixedSize(true);
        MainRV.setLayoutManager(new LinearLayoutManager(this));
        ExerciseList = new ArrayList<>();

        ExerciseList.add(new HomeCard("Hand Therapy - finger aerobics","qwertyuiopqwertyuiopqwertyuiopqwertyuiop",R.drawable.ic_touch_app_black_24dp));
        ExerciseList.add(new HomeCard("Eye Hand Coordination (Pattern Trace)","qwertyuiopqwertyuiopqwertyuiopqwertyuiop",R.drawable.ic_visibility_black_24dp));
        ExerciseList.add(new HomeCard("Letter and Sound","qwertyuiopqwertyuiopqwertyuiopqwertyuiop",R.drawable.ic_record_voice_over_black_24dp));
        ExerciseList.add(new HomeCard("Spatial Organization","qwertyuiopqwertyuiopqwertyuiopqwertyuiop",R.drawable.ic_data_usage_black_24dp));
        ExerciseList.add(new HomeCard("Art with Geometry","qwertyuiopqwertyuiopqwertyuiopqwertyuiop",R.drawable.ic_widgets_black_24dp));
        ExerciseList.add(new HomeCard("Letter Trace","qwertyuiopqwertyuiopqwertyuiopqwertyuiop",R.drawable.ic_title_black_24dp));

        MainRV.setAdapter(new ExcerciseAdapter());

        CheckUserAuth();

    }

    private void CheckUserAuth() {

        if(UserAuth.getCurrentUser() == null)
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }

    public class ExcerciseAdapter extends RecyclerView.Adapter<ExcerciseAdapter.ExerciseViewHolder>
    {

        @NonNull
        @Override
        public ExcerciseAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View card = LayoutInflater.from(getApplicationContext()).inflate(R.layout.home_cardview_layout,parent,false);
            return new ExcerciseAdapter.ExerciseViewHolder(card);
        }

        @Override
        public void onBindViewHolder(@NonNull ExcerciseAdapter.ExerciseViewHolder holder, final int position) {

            holder.ExerciseName.setText(ExerciseList.get(position).getName());
            holder.ExerciseDesc.setText(ExerciseList.get(position).getDesc());
            holder.ExerciseIcon.setImageDrawable(getDrawable(ExerciseList.get(position).getIcon()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0) {
                        Intent intent = getPackageManager().getLaunchIntentForPackage(packageNameGame);
                        if (intent != null) {
                            // We found the activity now start the activity
                            intent.putExtra("UID", UserAuth.getUid().toString());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            // Bring user to the market or let them choose an app?
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(Uri.parse("market://details?id=" + packageNameGame));
                            startActivity(intent);
                        }
                    }
                    if(position==1) {
                        Intent intent = getPackageManager().getLaunchIntentForPackage(packageNamePattern);
                        if (intent != null) {
                            // We found the activity now start the activity
                            intent.putExtra("UID", UserAuth.getUid().toString());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            // Bring user to the market or let them choose an app?
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(Uri.parse("market://details?id=" + packageNamePattern));
                            startActivity(intent);
                        }
                    }
                    if(position==2){
                        Intent intent = getPackageManager().getLaunchIntentForPackage(packageNameKidzee);
                        if (intent != null) {
                            // We found the activity now start the activity
                            intent.putExtra("UID",UserAuth.getUid().toString());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            // Bring user to the market or let them choose an app?
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(Uri.parse("market://details?id=" + packageNameKidzee));
                            startActivity(intent);
                        }
                    }
                    if(position==3){
                        Intent intent = getPackageManager().getLaunchIntentForPackage(packageNameConcentric);
                        if (intent != null) {
                            // We found the activity now start the activity
                            intent.putExtra("UID",UserAuth.getUid().toString());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            // Bring user to the market or let them choose an app?
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(Uri.parse("market://details?id=" + packageNameConcentric));
                            startActivity(intent);
                        }
                    }
                    if(position==5){
                        Intent intent = getPackageManager().getLaunchIntentForPackage(packageNameLetter);
                        if (intent != null) {
                            // We found the activity now start the activity
                            intent.putExtra("UID",UserAuth.getUid().toString());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            // Bring user to the market or let them choose an app?
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(Uri.parse("market://details?id=" + packageNameLetter));
                            startActivity(intent);
                        }
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return ExerciseList.size();
        }

        public class ExerciseViewHolder extends RecyclerView.ViewHolder {

            ImageView ExerciseIcon;
            TextView ExerciseName,ExerciseDesc;
            Button ExerciseTopScore;

            public ExerciseViewHolder(@NonNull View itemView) {
                super(itemView);

                ExerciseIcon =  itemView.findViewById(R.id.home_card_exercise_icon_IV);
                ExerciseName =  itemView.findViewById(R.id.home_card_exercise_name_TV);
                ExerciseDesc =  itemView.findViewById(R.id.home_card_exercise_description_TV);
                ExerciseTopScore =  itemView.findViewById(R.id.home_card_exercise_top_score_btn);

            }
        }
    }
}
