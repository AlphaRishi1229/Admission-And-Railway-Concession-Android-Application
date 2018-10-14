package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    Spinner spinner_sem, spinner_stream, spinner_caste;
    private String Stream_ID;

    Calendar calendar;
    int day, month, year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admission);

        etdate = (EditText)findViewById(R.id.etdate);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        spinner_sem = (Spinner)findViewById(R.id.spinner_sem_new);

        spinner_caste = (Spinner)findViewById(R.id.spinner_caste);
        List<String> caste_spin = new ArrayList<String>();
        caste_spin.add("S.C / S.T");
        caste_spin.add("OBC");
        caste_spin.add("General");
        ArrayAdapter<String> casteAdapter = new ArrayAdapter<String>(User_Admission_Activity.this, android.R.layout.simple_spinner_dropdown_item, caste_spin);
        casteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_caste.setAdapter(casteAdapter);

        spinner_stream = (Spinner)findViewById(R.id.spinner_stream_new);
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

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg3, arg2+1, arg1);
                }
            };

    private void showDate(int year, int month, int day) {
        etdate.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }

    private void setCsData()
    {
        setStreamID streamID;
        ArrayList<setStreamID> CsStream = new ArrayList<>();
        CsStream.add(new setStreamID("1","First Semester"));
        CsStream.add(new setStreamID("3","Third Semester"));
        CsStream.add(new setStreamID("5","Fifth Semester"));

        ArrayAdapter<setStreamID> adapter = new ArrayAdapter<setStreamID>(User_Admission_Activity.this,android.R.layout.simple_spinner_dropdown_item,CsStream);
        spinner_sem.setAdapter(adapter);

        int position = adapter.getPosition(new setStreamID("1","First Semester"));
        spinner_sem.setSelection(position);
    }

    private void setBmmData()
    {
        ArrayList<setStreamID> CsStream = new ArrayList<>();
        CsStream.add(new setStreamID("7","First Semester"));
        CsStream.add(new setStreamID("9","Third Semester"));
        CsStream.add(new setStreamID("11","Fifth Semester"));

        ArrayAdapter<setStreamID> adapter = new ArrayAdapter<setStreamID>(User_Admission_Activity.this,android.R.layout.simple_spinner_dropdown_item,CsStream);
        spinner_sem.setAdapter(adapter);
    }

    private void setBmsData()
    {
        ArrayList<setStreamID> CsStream = new ArrayList<>();
        CsStream.add(new setStreamID("13","First Semester"));
        CsStream.add(new setStreamID("15","Third Semester"));
        CsStream.add(new setStreamID("17","Fifth Semester"));

        ArrayAdapter<setStreamID> adapter = new ArrayAdapter<setStreamID>(User_Admission_Activity.this,android.R.layout.simple_spinner_dropdown_item,CsStream);
        spinner_sem.setAdapter(adapter);
    }
}
