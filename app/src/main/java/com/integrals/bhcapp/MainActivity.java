package com.integrals.bhcapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.integrals.bhcapp.Auth.LoginActivity;
import com.integrals.bhcapp.Helper.HomeCard;


import java.util.ArrayList;
import java.util.HashMap;
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
    private String packageNameCircle="com.example.circle_draw";
    private String packageNameWriteIt="co.martinbaciga.drawingtest";
    private DatabaseReference databaseReference;
    private String email;
    private String username;
    private String class_;
    private String school;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();




        UserAuth = FirebaseAuth.getInstance();
        CheckUserAuth();

        MainRV = findViewById(R.id.main_RV);
        MainRV.setHasFixedSize(true);
        MainRV.setLayoutManager(new LinearLayoutManager(this));
        ExerciseList = new ArrayList<>();

        ExerciseList.add(new HomeCard("Hand Therapy - finger aerobics","These pinching exercises encourage fine motor coordination and hand control. The principle behind this activity that involves pinching or gripping against a resistive force will increase hand strength.",R.drawable.ic_touch_app_black_24dp));
        ExerciseList.add(new HomeCard("Eye Hand Coordination (Pattern Trace)","Tracing activity helps the children to orient their movements from top to bottom and left to right. It also helps the children to develop their fine motor control.",R.drawable.ic_visibility_black_24dp));
        ExerciseList.add(new HomeCard("Letter and Sound","Letter-sound knowledge will allow children to make the link between the unfamiliar print words to their spoken languages.",R.drawable.ic_record_voice_over_black_24dp));
        ExerciseList.add(new HomeCard("Spatial Organization","Spatial Perceptual activity helps the children to lay work out well on the page and make the handwriting look fair",R.drawable.ic_data_usage_black_24dp));
        ExerciseList.add(new HomeCard("Art with Geometry"," Drawing geometrical shapes helps the children to develop the visual-spatial skills needed for mathematics. Also, it helps the children have good control over drawing vertical and horizontal lines.",R.drawable.ic_widgets_black_24dp));
        ExerciseList.add(new HomeCard("Letter Trace","This activity uses the hand position to help the child to understand the letter size and shape. It also enables the orthographic coding skills in children to remember the words or letters formation.\n",R.drawable.ic_title_black_24dp));
        ExerciseList.add(new HomeCard("Write it","This activity allows you to try words by yourself",R.drawable.ic_edit_black_24dp));

        MainRV.setAdapter(new ExcerciseAdapter());
        databaseReference= FirebaseDatabase.getInstance().getReference();



        findViewById(R.id.floatbuttonLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(MainActivity.this)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle("Confirm log out")
                        .setIcon(R.drawable.ic_warning_black_24dp)
                        .addButton("    Yes  ", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE,
                                CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseAuth.getInstance().signOut();
                                        recreate();
                                    }
                                });

                builder.show();
            }
        });
        if(isConnectedToNet(getApplicationContext())){

        }else{
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(MainActivity.this)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                    .setTitle("No internet ")
                    .setMessage("No internet. Please turn it ON")
                    .setDialogBackgroundColor(MainActivity.this.getResources().getColor(R.color.cfdialog_button_white_text_color))
                    .setIcon(R.drawable.ic_cancel_black_24dp)
                    .addButton("  CANCEL ", -1, Color.RED,
                            CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

            builder.show();


        }
    }

    private void CheckUserAuth() {

        if(UserAuth.getCurrentUser() == null)
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }else{
            email=FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

        }

    }


    @Override
    protected void onStart() {
        super.onStart();
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
                            intent.putExtra("UID", email);
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
                            intent.putExtra("UID", email);
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
                            intent.putExtra("UID",email);
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
                            intent.putExtra("UID",email);
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
                    if(position==4){
                        Intent intent = getPackageManager().getLaunchIntentForPackage(packageNameCircle);
                        if (intent != null) {
                            // We found the activity now start the activity
                            intent.putExtra("UID",email);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            // Bring user to the market or let them choose an app?
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(Uri.parse("market://details?id=" + packageNameCircle));
                            startActivity(intent);
                        }
                    }
                    if(position==5){
                        Intent intent = getPackageManager().getLaunchIntentForPackage(packageNameLetter);
                        if (intent != null) {
                            // We found the activity now start the activity
                            intent.putExtra("UID",email);
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
                    if(position==6){
                        Intent intent = getPackageManager().getLaunchIntentForPackage(packageNameWriteIt);
                        if (intent != null) {
                            // We found the activity now start the activity
                            intent.putExtra("UID",email);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            // Bring user to the market or let them choose an app?
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(Uri.parse("market://details?id=" + packageNameWriteIt));
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
    public  boolean isConnectedToNet(Context c) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
