package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class AdminConcessionActivity extends AppCompatActivity {

    TextInputEditText etadminid, etpass, etnum;
    Button btnlogin, btnsignup;
    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;
    SharedPreferences sp;

    String dbphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_concession);

        etadminid = findViewById(R.id.etadminid);
        etpass = findViewById(R.id.etpass);
        etnum = findViewById(R.id.etnum);

        sp = getSharedPreferences("login",MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            goToMainActivity();
        }

        btnlogin = findViewById(R.id.btnloginconcession);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etadminid.getText().toString();
                String password = etpass.getText().toString();
                String phone = etnum.getText().toString();
                try {
                    String msg;
                    ConnectionHelper conStr = new ConnectionHelper();
                    connect = conStr.connection();        // Connect to database
                    if (connect == null) {
                        ConnectionResult = "Check Your Internet Access!";
                    } else {
                        String query = "{call usp_GetAdminCredentials(?)}";
                        CallableStatement stmt = connect.prepareCall(query);
                        stmt.setString(1, username);
                        ResultSet rs = stmt.executeQuery();
                        msg = "Successfully inserted data";
                        while (rs.next()) {
                            String dbpwd = rs.getString("AdminPwd");
                            dbphone = rs.getString("AdminPhone");

                            if(password.equals(dbpwd) && phone.equals(dbphone))
                            {
                                goToMainActivity();
                                sp.edit().putBoolean("logged",true).apply();

                                Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_LONG).show();
                                Intent myintent = new Intent(AdminConcessionActivity.this, AdminAuthActivity2.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("phone",dbphone);
                                bundle.putString("Admin","AdminConcessionActivity");
                                myintent.putExtras(bundle);
                                startActivity(myintent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Login UnSuccess",Toast.LENGTH_LONG).show();

                            }

                        }
                        ConnectionResult = " successful";
                        isSuccess = true;
                        connect.close();
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    ConnectionResult = ex.getMessage();
                }

            }
        });

        btnsignup = findViewById(R.id.btnsignup);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(AdminConcessionActivity.this, AdminSignUpActivity.class);
                startActivity(signup);
            }
        });
    }
    public void goToMainActivity(){
        Intent i = new Intent(this,AdminConcessionDataActivity.class);
        startActivity(i);
    }
}

