package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;


public class ExistingStudentFragment extends Fragment {

    EditText disble_name_editText, disable_stream_editText;
    Button btnExisting, btnNew;
    Button btnShow, btnSubmit;
    DatePicker last_pass_date;
    ArrayList<MyItem> myitems = new ArrayList<>();
    ArrayList<MyItem> myitems2 = new ArrayList<>();
    ArrayList<MyItem> myitems3 = new ArrayList<>();
    MyAdapter myAdapter, myAdapter2, myAdapter3;
    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;
    Spinner spinner_pass, spinner_period, new_spinner_pass, new_spinner_period;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.existing_student_fragment, container, false);
        getTextView();
        getTextView2();
        getTextView3();

        final TextInputEditText etText = (TextInputEditText) view.findViewById(R.id.etText);
        final ListView listView, listView2, listView3;

        disble_name_editText = (EditText) view.findViewById(R.id.disable_name_editText);
        disble_name_editText.setKeyListener(null);
        disable_stream_editText = (EditText) view.findViewById(R.id.disable_stream_editText);
        disable_stream_editText.setKeyListener(null);

        listView = (ListView) view.findViewById(R.id.list_items);
        myAdapter = new MyAdapter(getContext(), myitems);

        listView2 = (ListView) view.findViewById(R.id.list_items2);
        myAdapter2 = new MyAdapter(getContext(), myitems2);

        listView3 = (ListView) view.findViewById(R.id.list_items3);
        myAdapter3 = new MyAdapter(getContext(), myitems3);

        listView.setAdapter(myAdapter);
        listView2.setAdapter(myAdapter2);
        listView3.setAdapter(myAdapter3);

        spinner_pass = (Spinner) view.findViewById(R.id.spin_passtype);
        List<String> passtype = new ArrayList<String>();
        passtype.add("Ist Class");
        passtype.add("IInd Class");
        ArrayAdapter<String> SpinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, passtype);
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_pass.setAdapter(SpinnerAdapter);

        spinner_period = (Spinner) view.findViewById(R.id.spin_period);
        List<String> pass_period = new ArrayList<String>();
        pass_period.add("Monthly");
        pass_period.add("Quaterly");
        ArrayAdapter<String> SpinnerAdapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, pass_period);
        SpinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_period.setAdapter(SpinnerAdapter2);

        new_spinner_pass = (Spinner) view.findViewById(R.id.new_spin_passtype);
        List<String> new_passtype = new ArrayList<String>();
        new_passtype.add("Ist Class");
        new_passtype.add("IInd Class");
        ArrayAdapter<String> SpinnerAdapter3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, new_passtype);
        SpinnerAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        new_spinner_pass.setAdapter(SpinnerAdapter3);

        new_spinner_period = (Spinner) view.findViewById(R.id.new_spin_period);
        List<String> new_pass_period = new ArrayList<String>();
        new_pass_period.add("Monthly");
        new_pass_period.add("Quaterly");
        ArrayAdapter<String> SpinnerAdapter4 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, new_pass_period);
        SpinnerAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        new_spinner_period.setAdapter(SpinnerAdapter4);

        last_pass_date = (DatePicker)view.findViewById(R.id.last_pass_date);
        btnExisting = (Button) view.findViewById(R.id.btnExisting);
        btnNew = (Button) view.findViewById(R.id.btnNew);
        btnShow = (Button) view.findViewById(R.id.btnshow);
        btnSubmit = (Button) view.findViewById(R.id.btnsubmit);


        btnExisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserConcessionActivity) getActivity()).setViewPager(0);
            }
        });
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserConcessionActivity) getActivity()).setViewPager(1);
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Admission_no = myAdapter.getValueFromEditText(0);
                String roll = myAdapter.getValueFromEditText(1);
                try {
                    ConnectionHelper conStr = new ConnectionHelper();
                    connect = conStr.connection();        // Connect to database
                    if (connect == null) {
                        ConnectionResult = "Check Your Internet Access!";
                    } else {
                        String query = "{call usp_GetStudentDetailsforConcession(?,?)}";
                        CallableStatement stmt = connect.prepareCall(query);
                        stmt.setString(1, Admission_no);
                        stmt.setString(2,roll);
                        ResultSet rs = stmt.executeQuery();
                        while (rs.next()) {
                            String name = rs.getString("STUDENT_NAME");
                            String stream = rs.getString("STREAM_NAME");
                            disble_name_editText.setText(name);
                            disable_stream_editText.setText(stream);
                        }
                        ConnectionResult = " successful";
                        isSuccess = true;
                        connect.close();
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    ConnectionResult = ex.getMessage();
                    Toast.makeText(getActivity(),ConnectionResult,Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String last_vch_no = myAdapter2.getValueFromEditText(0);
                String last_ticket_from = myAdapter2.getValueFromEditText(1);
                String last_ticket_no = myAdapter2.getValueFromEditText(2);
                String current_ticket_from = myAdapter3.getValueFromEditText(0);

                if (last_vch_no.equals("") || last_ticket_from.equals("") || last_ticket_no.equals("")
                        || current_ticket_from.equals("")) {
                    Toast.makeText(getActivity(), "Please fill all the details!", Toast.LENGTH_SHORT).show();
                } else {
                    Send objSend = new Send();
                    objSend.execute("");
                }
            }
        });
        return view;
    }

    private void getTextView() {

        myitems.add(new MyItem("Admission Number:"));
        myitems.add(new MyItem("Roll Number:"));
        myitems.add(new MyItem("Name Of Student:"));
        myitems.add(new MyItem("Stream:"));
    }

    private void getTextView2() {
        myitems2.add(new MyItem("Last Certificate Number(VCH No):"));
        myitems2.add(new MyItem("Last Ticket from(Station):"));
        myitems2.add(new MyItem("Last Ticket Number(Last 4 digits):"));
    }

    private void getTextView3() {
        myitems3.add(new MyItem("From Station:"));
    }

    private class Send extends AsyncTask<String, String, String> {
        String msg = "";
        String Admission_no = myAdapter.getValueFromEditText(0);
        String roll = myAdapter.getValueFromEditText(1);
        String last_vch_no = myAdapter2.getValueFromEditText(0);
        String last_ticket_from = myAdapter2.getValueFromEditText(1);
        String last_ticket_no = myAdapter2.getValueFromEditText(2);
        String last_issue_date = last_pass_date.getYear()+"-"+(last_pass_date.getMonth()+1)+"-"+last_pass_date.getDayOfMonth();
        String pass_type = spinner_pass.getSelectedItem().toString();
        String last_ticket_period = spinner_period.getSelectedItem().toString();
        String current_ticket_from = myAdapter3.getValueFromEditText(0);
        String current_ticket_type = new_spinner_pass.getSelectedItem().toString();
        String current_ticket_period = new_spinner_period.getSelectedItem().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
               // Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnectionHelper conStr = new ConnectionHelper();
                connect = conStr.connection();
                if (connect == null) {
                    msg = "Check connection";
                    Toast.makeText(getActivity(), "Check Connection", Toast.LENGTH_SHORT).show();
                } else {
                    String query = "{call usp_Insert_Existing_RailwayConcession(?,?,?,?,?,?,?,?,?,?,?)}";
                    CallableStatement stmt = connect.prepareCall(query);
                    stmt.setString(1, Admission_no);
                    stmt.setString(2,roll);
                    stmt.setString(3,last_vch_no);
                    stmt.setString(4,last_ticket_no);
                    stmt.setString(5,last_ticket_from);
                    stmt.setString(6,last_ticket_period);
                    stmt.setString(7,last_issue_date);
                    stmt.setString(8,pass_type);
                    stmt.setString(9,current_ticket_from);
                    stmt.setString(10,current_ticket_type);
                    stmt.setString(11,current_ticket_period);
                    ResultSet rs = stmt.executeQuery();
                    msg = "Successfully inserted data";
                    ConnectionResult = " successful";
                    isSuccess = true;
                    connect.close();
                }
                connect.close();
            } catch (SQLException se) {
                msg = "Data Exception";
                Log.e("error here 1 :  ", se.getMessage());
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            if(isSuccess) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Railway Concession Entry Added");
                builder.setMessage("You have successfully applied for Railway Concession Form. Your Code is:" + getCode());
                builder.setPositiveButton(
                        "DONE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
            else
            {
                Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
            }
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
                        String query = "select pass_code from dbo.railway_concession where Admission_no =" + "'" + Admission_no + "'" + "and last_vch_no =" + "'" + last_vch_no+ "'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            code = rs.getInt("pass_code");
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

}
