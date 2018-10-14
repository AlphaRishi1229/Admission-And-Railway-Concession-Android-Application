package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AdminConcessionDataActivity extends AppCompatActivity {

    Button btnsignout, btn_download_all;
    SharedPreferences sp;

    Connection connect;
    String ConnectionResult;
    Boolean isSuccess;

    private ArrayList<ConcessionRecycledItems> itemsArrayList;
    private RecycledConcessionItemsAdapter myAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_concession_data);

        sp = getSharedPreferences("login",MODE_PRIVATE);

        recyclerView = (RecyclerView) findViewById(R.id.concession_recycle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        itemsArrayList = new ArrayList<ConcessionRecycledItems>();
        myAdapter = new RecycledConcessionItemsAdapter(itemsArrayList, getApplicationContext());
        recyclerView.setAdapter(myAdapter);

        btnsignout = (Button)findViewById(R.id.btnsignout);
        btnsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
                sp.edit().putBoolean("logged",false).apply();
            }
        });

        btn_download_all = (Button)findViewById(R.id.btn_download_all);
        btn_download_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://104.211.167.104/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        SyncConcession concession = new SyncConcession();
        concession.execute("");
    }
    public void goToMainActivity(){
        Intent i = new Intent(this,MainScreenActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent backintent = new Intent(AdminConcessionDataActivity.this, MainScreenActivity.class);
        startActivity(backintent);
    }

    public class SyncConcession extends AsyncTask<String, String, String>
    {
        String msg = "";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(AdminConcessionDataActivity.this, "Synchronising",
                    "Database Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                ConnectionHelper conStr = new ConnectionHelper();
                connect = conStr.connection();        // Connect to database
                if (connect == null) {
                    ConnectionResult = "Check Your Internet Access!";
                } else {
                    String query = "{call usp_GetAllConcessionDetails()}";
                    CallableStatement stmt = connect.prepareCall(query);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String pass_code = rs.getString("pass_code");
                        String Admno = rs.getString("Admission_no");
                        String rollno = rs.getString("roll_no");
                        String station_name = rs.getString("current_station_name");
                        String pass_type = rs.getString("current_pass_type");
                        String pass_period = rs.getString("current_ticket_period");

                        itemsArrayList.add(new ConcessionRecycledItems(pass_code,Admno,rollno,station_name,pass_type,pass_period));
                    }
                    ConnectionResult = " successful";
                    isSuccess = true;
                    connect.close();
                }
            } catch (Exception ex) {
                isSuccess = false;
                ConnectionResult = ex.getMessage();
                Toast.makeText(getApplicationContext(), ConnectionResult, Toast.LENGTH_LONG).show();
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            progress.dismiss();
            if (isSuccess == false) {
            } else {
                try {
                    myAdapter = new RecycledConcessionItemsAdapter(itemsArrayList, getApplicationContext());
                    recyclerView.setAdapter(myAdapter);
                } catch (Exception ex) {

                }
            }
        }

    }
}

