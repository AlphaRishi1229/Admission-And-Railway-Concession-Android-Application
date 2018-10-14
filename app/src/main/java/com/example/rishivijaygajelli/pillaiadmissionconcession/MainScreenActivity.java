package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.support.design.widget.CoordinatorLayout;


public class MainScreenActivity extends AppCompatActivity {
Button btn1, btn2;
Switch switch1;
CoordinatorLayout coordinatorLayout;
RelativeLayout rel1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        switch1 = (Switch)findViewById(R.id.switch1);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        rel1 =(RelativeLayout)findViewById(R.id.rel1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true)
                {
                    rel1.setBackground(getResources().getDrawable(R.mipmap.pillai));
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Admin Mode Enabled", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    switch1.setChecked(false);
                                    Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "User Mode Enabled", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            });

                    snackbar.show();
                }
                else if(!isChecked)
                {
                    rel1.setBackgroundColor(getResources().getColor(R.color.grey));
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switch1.isChecked())
                {
                    Intent adminIntent = new Intent(MainScreenActivity.this, Admin_Admission_Activity.class);
                    startActivity(adminIntent);
                }
                else
                {
                    Intent userIntent = new Intent(MainScreenActivity.this, User_Admission_Activity.class);
                    startActivity(userIntent);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switch1.isChecked())
                {
                    Intent adminIntent2 = new Intent(MainScreenActivity.this, AdminConcessionActivity.class);
                    startActivity(adminIntent2);
                }
                else
                {
                    Intent userIntent2 = new Intent(MainScreenActivity.this, UserConcessionActivity.class);
                    startActivity(userIntent2);
                }
            }
        });
    }
    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }
}

