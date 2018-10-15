package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AdminAdmissionAddActivity extends AppCompatActivity {

    Button btnaddadm, btnshowadm, btnshowapplied;
    Button btnlogout;
    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private ViewPager viewPager;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_admission_add);

        sp = getSharedPreferences("adm_login",MODE_PRIVATE);

        sectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        btnlogout = findViewById(R.id.btnlogout);
        btnaddadm = findViewById(R.id.btnaddadm);
        btnshowadm = findViewById(R.id.btnshowadm);
        btnshowapplied = findViewById(R.id.btnshowapplied);

        btnaddadm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAdmissionAddActivity.this.setViewPager(0);
            }
        });
        btnshowadm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAdmissionAddActivity.this.setViewPager(1);

            }
        });
        btnshowapplied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAdmissionAddActivity.this.setViewPager(2);

            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
                sp.edit().putBoolean("logged",false).apply();
            }
        });

    }
    public void goToMainActivity(){
        Intent i = new Intent(this,MainScreenActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent backintent = new Intent(AdminAdmissionAddActivity.this, MainScreenActivity.class);
        startActivity(backintent);
    }
    private void setupViewPager(ViewPager viewPager)
    {
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewAdmissionFragment(),"Add Student to database");
        adapter.addFragment(new AdminDatabaseFragment(),"Check Database");
        adapter.addFragment(new AdmissionFormsFragment(),"Admission Forms");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager (int FragmentNumber)
    {
        viewPager.setCurrentItem(FragmentNumber);
    }
}

