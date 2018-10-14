package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UserConcessionActivity extends AppCompatActivity {

    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_concession);

        sectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new ExistingStudentFragment(),"Existing Student");
        adapter.addFragment(new NewStudentFragment(),"New Student");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager (int FragmentNumber)
    {
        viewPager.setCurrentItem(FragmentNumber);
    }
}
