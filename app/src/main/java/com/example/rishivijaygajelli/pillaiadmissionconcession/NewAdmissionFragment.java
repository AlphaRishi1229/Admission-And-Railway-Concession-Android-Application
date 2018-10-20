package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class NewAdmissionFragment extends Fragment {
TextInputEditText admno, nameofstudent,rollno;
Spinner spinner_stream, spinner_sem;
Button btnadd;
    private String Stream_ID;
    Connection connect;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_new_admission, container, false);

//        String AdmNum = getArguments().getString("AdmNo");
        admno = view.findViewById(R.id.admno);
        nameofstudent = view.findViewById(R.id.nameofstudent);
        rollno = view.findViewById(R.id.rollno);

      //  admno.setText(AdmNum);
        spinner_sem = view.findViewById(R.id.spinner_sem);
        spinner_stream = view.findViewById(R.id.spinner_stream);
        List<String> stream_spin = new ArrayList<String>();
        stream_spin.add("Bsc Computer Science");
        stream_spin.add("B.M.M");
        stream_spin.add("B.M.S");
        ArrayAdapter<String> SpinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, stream_spin);
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
         btnadd = view.findViewById(R.id.btnadd);
         btnadd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String AdmissionNo = admno.getText().toString();
                 String StudentName = nameofstudent.getText().toString();
                 String RollNo = rollno.getText().toString();
                 String StreamNo = Stream_ID;

               if(AdmissionNo.equals("")||StudentName.equals("")||RollNo.equals("")||StreamNo.equals(""))
               {
                   Toast.makeText(getActivity(),"Fill all the details",Toast.LENGTH_SHORT).show();
               }
               else
               {
                   AddStu addStu = new AddStu();
                   addStu.execute("");
               }
             }
         });

        return view;
    }

    private void setCsData()
    {
        setStreamID streamID;
        ArrayList<setStreamID> CsStream = new ArrayList<>();
        CsStream.add(new setStreamID("1","First Semester"));
        CsStream.add(new setStreamID("2","Second Semester"));
        CsStream.add(new setStreamID("3","Third Semester"));
        CsStream.add(new setStreamID("4","Fourth Semester"));
        CsStream.add(new setStreamID("5","Fifth Semester"));
        CsStream.add(new setStreamID("6","Sixth Semester"));

        ArrayAdapter<setStreamID> adapter = new ArrayAdapter<setStreamID>(getContext(),android.R.layout.simple_spinner_dropdown_item,CsStream);
        spinner_sem.setAdapter(adapter);

        int position = adapter.getPosition(new setStreamID("1","First Semester"));
        spinner_sem.setSelection(position);
    }

    private void setBmmData()
    {
        ArrayList<setStreamID> CsStream = new ArrayList<>();
        CsStream.add(new setStreamID("7","First Semester"));
        CsStream.add(new setStreamID("8","Second Semester"));
        CsStream.add(new setStreamID("9","Third Semester"));
        CsStream.add(new setStreamID("10","Fourth Semester"));
        CsStream.add(new setStreamID("11","Fifth Semester"));
        CsStream.add(new setStreamID("12","Sixth Semester"));

        ArrayAdapter<setStreamID> adapter = new ArrayAdapter<setStreamID>(getContext(),android.R.layout.simple_spinner_dropdown_item,CsStream);
        spinner_sem.setAdapter(adapter);
    }

    private void setBmsData()
    {
        ArrayList<setStreamID> CsStream = new ArrayList<>();
        CsStream.add(new setStreamID("13","First Semester"));
        CsStream.add(new setStreamID("14","Second Semester"));
        CsStream.add(new setStreamID("15","Third Semester"));
        CsStream.add(new setStreamID("16","Fourth Semester"));
        CsStream.add(new setStreamID("17","Fifth Semester"));
        CsStream.add(new setStreamID("18","Sixth Semester"));

        ArrayAdapter<setStreamID> adapter = new ArrayAdapter<setStreamID>(getContext(),android.R.layout.simple_spinner_dropdown_item,CsStream);
        spinner_sem.setAdapter(adapter);
    }

    private class AddStu extends AsyncTask<String,String,String>
    {
        String msg = "";
        String AdmissionNo = admno.getText().toString();
        String StudentName = nameofstudent.getText().toString();
        String RollNo = rollno.getText().toString();
        String StreamNo = Stream_ID;


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
                    Toast.makeText(getActivity(), "Check Connection", Toast.LENGTH_SHORT).show();
                } else {
                    String query = "{call usp_Insert_StudentMaster(?,?,?,?)}";
                    CallableStatement stmt = connect.prepareCall(query);
                    stmt.setString(1,StudentName);
                    stmt.setString(2,RollNo);
                    stmt.setString(3,StreamNo);
                    stmt.setString(4,AdmissionNo);
                    ResultSet rs = stmt.executeQuery();
                }
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
            admno.setText("");
            nameofstudent.setText("");
            rollno.setText("");

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(AdmissionNo+":"+StudentName);
            builder.setMessage("Student has been added to the database.");
            builder.setPositiveButton(
                    "DONE",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.show();
        }
    }
}
