package com.integrals.bhcapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.integrals.bhcapp.Auth.LoginActivity;
import com.integrals.bhcapp.Helper.HomeCard;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth UserAuth;
    RecyclerView MainRV;
    List<HomeCard> ExerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserAuth = FirebaseAuth.getInstance();
        MainRV = findViewById(R.id.main_RV);
        MainRV.setHasFixedSize(true);
        MainRV.setLayoutManager(new LinearLayoutManager(this));
        ExerciseList = new ArrayList<>();

        ExerciseList.add(new HomeCard("Hand Therapy - finger aerobics","qwertyuiopqwertyuiopqwertyuiopqwertyuiop"));
        ExerciseList.add(new HomeCard("Eye Hand Coordination (Pattern Trace)","qwertyuiopqwertyuiopqwertyuiopqwertyuiop"));
        ExerciseList.add(new HomeCard("Letter and Sound","qwertyuiopqwertyuiopqwertyuiopqwertyuiop"));
        ExerciseList.add(new HomeCard("Spatial Organization","qwertyuiopqwertyuiopqwertyuiopqwertyuiop"));
        ExerciseList.add(new HomeCard("Art with Geometry","qwertyuiopqwertyuiopqwertyuiopqwertyuiop"));
        ExerciseList.add(new HomeCard("Letter Trace","qwertyuiopqwertyuiopqwertyuiopqwertyuiop"));

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
        public void onBindViewHolder(@NonNull ExcerciseAdapter.ExerciseViewHolder holder, int position) {

            holder.ExerciseName.setText(ExerciseList.get(position).getName());
            holder.ExerciseDesc.setText(ExerciseList.get(position).getDesc());

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
