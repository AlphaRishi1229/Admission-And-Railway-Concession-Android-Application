package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminSignUpActivity extends AppCompatActivity {

    EditText etnewusername, etnewpassword, etnewphone;
    Button btnsignup;
    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);

        etnewusername = (EditText)findViewById(R.id.etnewusername);
        etnewpassword = (EditText)findViewById(R.id.etnewpassword);
        etnewphone = (EditText)findViewById(R.id.etnewphone);

        btnsignup = (Button)findViewById(R.id.btnsignup);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etnewusername.getText().toString();
                String password = etnewpassword.getText().toString();
                String phone = etnewphone.getText().toString();

                if(username.equals("") || password.equals("") || phone.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please fill all the details!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Send objSend = new Send();
                    objSend.execute("");
                }
            }
        });

    }

    private class Send extends AsyncTask<String, String, String> {

        String msg = "";
        String username = etnewusername.getText().toString();
        String password = etnewpassword.getText().toString();
        String phone = etnewphone.getText().toString();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnectionHelper conStr = new ConnectionHelper();
                connect = conStr.connection();
                if (connect == null) {
                    msg = "Check connection";
                    Toast.makeText(getApplicationContext(), "Check Connection", Toast.LENGTH_SHORT).show();
                } else {
                    String query = "Insert into dbo.admin_authentication(AdminID, AdminPwd, AdminPhone)" +
                            "values ('"+username+"','"+password+"','"+phone+"')";

                    Statement statement = connect.createStatement();
                    statement.executeUpdate(query);
                    msg = "Successfully inserted data";
                }
                connect.close();
            } catch (SQLException se) {
                msg = "Sql Exception";
//              Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                Log.e("error here 1 :  ", se.getMessage());
            } catch (ClassNotFoundException e) {
                msg = "Class Not Found";
                //                        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
                Log.e("error here 2 : ", e.getMessage());
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            String otpPhone = "7977052177";
            Intent otpIntent = new Intent(AdminSignUpActivity.this, AdminAuthActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("username",username);
            bundle.putString("phone",otpPhone);
            otpIntent.putExtras(bundle);
            startActivity(otpIntent);

        }
    }

}
