package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminAdmissionAddActivity extends AppCompatActivity {

    Button btnaddadm, btnshowadm, btnshowapplied;
    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_admission_add);

        sectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        btnaddadm = (Button)findViewById(R.id.btnaddadm);
        btnshowadm = (Button)findViewById(R.id.btnshowadm);
        btnshowapplied = (Button)findViewById(R.id.btnshowapplied);

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

