package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class AdminAuthActivity2 extends AppCompatActivity {
    Button btnverify, btnresend;
    private static final String TAG = "PhoneAuth";
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    EditText etotp,etphoneno;
    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;
    String verificationCode;

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_auth2);
        auth = FirebaseAuth.getInstance();
        etotp = findViewById(R.id.etotp);

        sp = getSharedPreferences("login",MODE_PRIVATE);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.d(TAG, "Invalid credential: "
                            + e.getLocalizedMessage());
                    Toast.makeText(getApplicationContext(),"Invalid Credentials"+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // SMS quota exceeded
                    Log.d(TAG, "SMS Quota exceeded.");
                    Toast.makeText(getApplicationContext(),"SMS Quota exceeded",Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(),TAG,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(getApplicationContext(),"Verification Code Sent",Toast.LENGTH_SHORT).show();
            }
        };

        Bundle bundle = getIntent().getExtras();

        if(bundle.containsKey("Admin"))
        {
            String phone_no = bundle.getString("phone");
            String code_phone_no = "+91"+phone_no;
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    code_phone_no,30, TimeUnit.SECONDS,this,mCallbacks
            );
        }
        else if(bundle.containsKey("User"))
        {
            String new_phone = bundle.getString("new_phone");
            String code_phone_no_2 = "+91"+new_phone;
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    code_phone_no_2,30, TimeUnit.SECONDS,this,mCallbacks
            );
        }



        btnverify = findViewById(R.id.btnverify);
        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_code = etotp.getText().toString();


                verifyPhonenumber(verificationCode,input_code);

            }
        });

    }


    public void signInwithPhone(PhoneAuthCredential credential)
    {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String msg = "";

                        if(task.isSuccessful())
                        {
                            Bundle bundle = getIntent().getExtras();
                            if (bundle.containsKey("Admin"))
                            {
                                sp.edit().putBoolean("logged",true).apply();
                                Toast.makeText(getApplicationContext(),"Admin Logged In Successfully",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(AdminAuthActivity2.this, AdminConcessionDataActivity.class);
                                startActivity(i);
                            }
                            else if(bundle.containsKey("User"))
                            {
                              NewStudentData newsd = new NewStudentData();
                              newsd.execute("");

                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Invalid Code",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void verifyPhonenumber(String verifycode, String inputcode)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifycode,inputcode);
        signInwithPhone(credential);
    }

    public void setBtnresend(View view)
    {
        Bundle bundle = getIntent().getExtras();

        if(bundle.containsKey("Admin"))
        {
            String phone_no = bundle.getString("phone");
            String code_phone_no = "+91"+phone_no;
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    code_phone_no,60, TimeUnit.SECONDS,this,mCallbacks
            );
        }
        else if(bundle.containsKey("User"))
        {
            String new_phone = bundle.getString("new_phone");
            String code_phone_no_2 = "+91"+new_phone;
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    code_phone_no_2,60, TimeUnit.SECONDS,this,mCallbacks
            );
        }

        /*String phone_no = bundle.getString("phone");
        String code_phone_no = "+91"+phone_no;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                code_phone_no,60, TimeUnit.SECONDS,this,mCallbacks
        );*/
    }

    public class NewStudentData extends AsyncTask<String,String,String>
    {
        String msg = "";
        Bundle bundle2 = getIntent().getExtras();

        String new_phone = bundle2.getString("new_phone");
        String name = bundle2.getString("name");
        String address = bundle2.getString("address");
        String email = bundle2.getString("email");
        String caste = bundle2.getString("caste");
        String dob = bundle2.getString("dob");
        String Stream = bundle2.getString("StreamID");
        String clgname = bundle2.getString("clgname");
        String tenth = bundle2.getString("10thpercent");
        String twelth = bundle2.getString("12thpercent");

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
                    String query = "{call usp_Insert_Applied_Student_Details(?,?,?,?,?,?,?,?,?,?)}";
                    CallableStatement stmt = connect.prepareCall(query);
                    stmt.setString(1,name);
                    stmt.setString(2,address);
                    stmt.setString(3,new_phone);
                    stmt.setString(4,email);
                    stmt.setString(5,caste);
                    stmt.setString(6,dob);
                    stmt.setString(7,Stream);
                    stmt.setString(8,clgname);
                    stmt.setString(9,tenth);
                    stmt.setString(10,twelth);

                    ResultSet rs = stmt.executeQuery();
                }
                isSuccess = true;
                connect.close();
            } catch (SQLException se) {
                msg = "Sql Exception";
//               Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminAuthActivity2.this);
                builder.setTitle("Student Registeration Successful");
                builder.setMessage("You have successfully applied for Admission Form. Your Admit Code is:" + getCode()  +"\n This code " +
                        "will be useful during the Admission Process.");
                builder.setPositiveButton(
                        "DONE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(AdminAuthActivity2.this, MainScreenActivity.class);
                                startActivity(i);
                            }
                        });
                builder.show();

        }

        public int getCode()
        {
            int code=0;
            try {
                ConnectionHelper conStr = new ConnectionHelper();
                connect = conStr.connection();        // Connect to database
                if (connect == null) {
                    ConnectionResult = "Check Your Internet Access!";
                } else {
                    String query = "{call usp_GetStudentIDforNewStudent(?)}";
                    CallableStatement stmt = connect.prepareCall(query);
                    stmt.setString(1,new_phone);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        code = rs.getInt("NewStudentID");
                    }
                    ConnectionResult = " successful";
                    isSuccess = true;
                    connect.close();
                }
            } catch (Exception ex) {
                isSuccess = false;
                ConnectionResult = ex.getMessage();
            }
            return code;
        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(AdminAuthActivity2.this, MainScreenActivity.class);
        startActivity(i);
    }
}




