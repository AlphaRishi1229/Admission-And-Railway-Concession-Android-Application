package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class User_Admission_Activity extends AppCompatActivity {
    EditText etdate;
    TextInputEditText name, address, phone, email, dob, pclgname, tenth_marks, twelth_marks;
    Spinner spinner_sem, spinner_stream, spinner_caste;
    Button btnadmit;

    String new_name, new_address, new_phone, new_email, new_dob, new_pclgname, tenthmarks, twelthmarks;
    private String Stream_ID;
    Calendar calendar;
    int day, month, year;
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admission);

        etdate = findViewById(R.id.etdate);

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        pclgname = findViewById(R.id.pclgname);
        tenth_marks = findViewById(R.id.tenth_marks);
        twelth_marks = findViewById(R.id.twelth_marks);

        btnadmit = findViewById(R.id.btn_admit);
        btnadmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_name = name.getText().toString();
                new_address = address.getText().toString();
                new_phone = phone.getText().toString();
                new_email = email.getText().toString();
                new_dob = etdate.getText().toString();
                new_pclgname = pclgname.getText().toString();
                tenthmarks = tenth_marks.getText().toString();
                twelthmarks = twelth_marks.getText().toString();
                final String caste = spinner_caste.getSelectedItem().toString();

                if(new_name.equals("")|| new_address.equals("") || new_phone.equals("") || new_dob.equals("") || new_pclgname.equals("")
                        || tenthmarks.equals("") || twelthmarks.equals("") )
                {
                    Toast.makeText(getApplicationContext(),"Please Fill All the Fields (mandatory)!",Toast.LENGTH_LONG).show();
                }

                else
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(User_Admission_Activity.this);
                    builder.setTitle("Confirm Applicant Details?");
                    builder.setMessage("Are you sure that the details provided are true. Providing wrong information can result in cancellation of your Admission Form.\n" +
                            "Note: Once verified you wont be able to apply again. Are you sure to continue?");
                    builder.setPositiveButton(
                            "NEXT",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(User_Admission_Activity.this);
                                    builder1.setTitle("Verification");
                                    builder1.setMessage("Your phone number needs to be verified. Continue?");
                                    builder1.setPositiveButton("YES",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("User","UserAdmissionActivity");
                                                    bundle.putString("new_phone",new_phone);
                                                    bundle.putString("name",new_name);
                                                    bundle.putString("address",new_address);
                                                    bundle.putString("email",new_email);
                                                    bundle.putString("caste",caste);
                                                    bundle.putString("dob",new_dob);
                                                    bundle.putString("StreamID",Stream_ID);
                                                    bundle.putString("clgname",new_pclgname);
                                                    bundle.putString("10thpercent",tenthmarks);
                                                    bundle.putString("12thpercent",twelthmarks);

                                                    Intent i = new Intent(User_Admission_Activity.this, AdminAuthActivity2.class);
                                                    i.putExtras(bundle);
                                                    startActivity(i);
                                                }
                                            });
                                    builder1.show();
                                }
                            });
                    builder.show();
                }
            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        spinner_sem = findViewById(R.id.spinner_sem_new);

        spinner_caste = findViewById(R.id.spinner_caste);
        List<String> caste_spin = new ArrayList<String>();
        caste_spin.add("S.C / S.T");
        caste_spin.add("OBC");
        caste_spin.add("General");
        ArrayAdapter<String> casteAdapter = new ArrayAdapter<String>(User_Admission_Activity.this, android.R.layout.simple_spinner_dropdown_item, caste_spin);
        casteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_caste.setAdapter(casteAdapter);

        spinner_stream = findViewById(R.id.spinner_stream_new);
        List<String> stream_spin = new ArrayList<String>();
        stream_spin.add("Bsc Computer Science");
        stream_spin.add("B.M.M");
        stream_spin.add("B.M.S");
        ArrayAdapter<String> SpinnerAdapter = new ArrayAdapter<String>(User_Admission_Activity.this, android.R.layout.simple_spinner_item, stream_spin);
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_stream.setAdapter(SpinnerAdapter);

        spinner_stream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spinner_stream.getSelectedItem().equals("Bsc Computer Science")) {
                    setCsData();
                }
                else if(spinner_stream.getSelectedItem().equals("B.M.M")){
                    setBmmData();
                }
                else if(spinner_stream.getSelectedItem().equals("B.M.S")) {
                    setBmsData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setStreamID streamID = (setStreamID)parent.getSelectedItem();
                Stream_ID = streamID.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void showDate(int year, int month, int day) {
        etdate.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }

    private void setCsData()
    {
        setStreamID streamID;
        ArrayList<setStreamID> CsStream = new ArrayList<>();
        CsStream.add(new setStreamID("1","CS-First Semester"));
        CsStream.add(new setStreamID("3","CS-Third Semester"));
        CsStream.add(new setStreamID("5","CS-Fifth Semester"));

        ArrayAdapter<setStreamID> adapter = new ArrayAdapter<setStreamID>(User_Admission_Activity.this,android.R.layout.simple_spinner_dropdown_item,CsStream);
        spinner_sem.setAdapter(adapter);

        int position = adapter.getPosition(new setStreamID("1","First Semester"));
        spinner_sem.setSelection(position);
    }

    private void setBmmData()
    {
        ArrayList<setStreamID> CsStream = new ArrayList<>();
        CsStream.add(new setStreamID("7","BMM-First Semester"));
        CsStream.add(new setStreamID("9","BMM-Third Semester"));
        CsStream.add(new setStreamID("11","BMM-Fifth Semester"));

        ArrayAdapter<setStreamID> adapter = new ArrayAdapter<setStreamID>(User_Admission_Activity.this,android.R.layout.simple_spinner_dropdown_item,CsStream);
        spinner_sem.setAdapter(adapter);
    }

    private void setBmsData()
    {
        ArrayList<setStreamID> CsStream = new ArrayList<>();
        CsStream.add(new setStreamID("13","BMS-First Semester"));
        CsStream.add(new setStreamID("15","BMS-Third Semester"));
        CsStream.add(new setStreamID("17","BMS-Fifth Semester"));

        ArrayAdapter<setStreamID> adapter = new ArrayAdapter<setStreamID>(User_Admission_Activity.this,android.R.layout.simple_spinner_dropdown_item,CsStream);
        spinner_sem.setAdapter(adapter);
    }
}
