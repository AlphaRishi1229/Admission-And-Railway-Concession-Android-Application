package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;


public class AdminAuthActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_admin_auth);

        sp = getSharedPreferences("adm_login",MODE_PRIVATE);

        auth = FirebaseAuth.getInstance();
        etotp = findViewById(R.id.etotp);

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
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // SMS quota exceeded
                    Log.d(TAG, "SMS Quota exceeded.");
                }

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
               super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(getApplicationContext(),"Verification Code Sent",Toast.LENGTH_SHORT).show();
            }
        };

        Bundle bundle = getIntent().getExtras();
       String phone_no = bundle.getString("phone");
        String code_phone_no = "+91"+phone_no;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                code_phone_no,10, TimeUnit.SECONDS,this,mCallbacks
        );

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
                            sp.edit().putBoolean("logged",true).apply();
                            Toast.makeText(getApplicationContext(),"Admin Logged In Successfully",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(AdminAuthActivity.this, AdminAdmissionAddActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Invalid Code",Toast.LENGTH_LONG).show();
                            Bundle bundle = getIntent().getExtras();
                            String username = bundle.getString("username");

                            try {
                                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                                ConnectionHelper conStr = new ConnectionHelper();
                                connect = conStr.connection();
                                if (connect == null) {
                                    msg = "Check connection";
                                    Toast.makeText(getApplicationContext(), "Check Connection", Toast.LENGTH_SHORT).show();
                                } else {
                                    String query = "Delete from dbo.admin_authentication where AdminID ="+"'"+username+"'";

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
        String phone_no = bundle.getString("phone");
        String code_phone_no = "+91"+phone_no;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                code_phone_no,60, TimeUnit.SECONDS,this,mCallbacks
        );
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AdminAuthActivity.this, Admin_Admission_Activity.class);
        startActivity(i);
    }
}

