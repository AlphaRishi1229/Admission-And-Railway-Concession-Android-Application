package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Admin_Admission_Activity extends AppCompatActivity {
    TextInputEditText etusername, etpassword, etphone;
    Button btnlogin, btnsignup;
    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_admission);

        etusername = findViewById(R.id.etusername);
        etpassword = findViewById(R.id.etpassword);
        etphone = findViewById(R.id.etphone);

        sp = getSharedPreferences("adm_login",MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            goToMainActivity();
        }

        btnlogin = findViewById(R.id.btnloginconcession);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etusername.getText().toString();
                String password = etpassword.getText().toString();
                String phone = etphone.getText().toString();
                try {
                    ConnectionHelper conStr = new ConnectionHelper();
                    connect = conStr.connection();        // Connect to database
                    if (connect == null) {
                        ConnectionResult = "Check Your Internet Access!";
                    } else {
                        String query = "select AdminPwd , AdminPhone from dbo.admin_authentication where AdminID =" + "'" + username + "'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            String dbpwd = rs.getString("AdminPwd");
                            String dbphone = rs.getString("AdminPhone");

                           if(password.equals(dbpwd) && phone.equals(dbphone))
                            {
                                goToMainActivity();
                                Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_LONG).show();
                                Intent myintent = new Intent(Admin_Admission_Activity.this, AdminAuthActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("phone",phone);
                                myintent.putExtras(bundle);
                                startActivity(myintent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Login UNSuccess",Toast.LENGTH_LONG).show();

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
                Intent signup = new Intent(Admin_Admission_Activity.this, AdminSignUpActivity.class);
                startActivity(signup);
            }
        });
    }
    public void goToMainActivity(){
        Intent i = new Intent(this,AdminAdmissionAddActivity.class);
        startActivity(i);
    }
}
