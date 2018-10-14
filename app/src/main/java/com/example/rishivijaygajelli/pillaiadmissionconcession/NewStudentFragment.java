package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NewStudentFragment extends Fragment {

    private Activity mActivity;

    EditText disble_name_editText, disable_stream_editText;
    Button btnShow, btnSubmit;
    Button btnExisting, btnNew;
    Spinner spinner_pass,spin_period_new;

    ArrayList<MyItem> myitems_new=new ArrayList<>();
    ArrayList<MyItem> myitems2_new=new ArrayList<>();

    MyAdapter newmyAdapter, newmyAdapter2;
    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.new_student_fragment, container, false);

        getNewTextView();
        getNewTextView2();

        ListView newlistView, newlistView2;

        disble_name_editText = (EditText) view.findViewById(R.id.disable_name_editText);
        disble_name_editText.setKeyListener(null);
        disable_stream_editText = (EditText) view.findViewById(R.id.disable_stream_editText);
        disable_stream_editText.setKeyListener(null);

        newlistView = (ListView)view.findViewById(R.id.list_items_newstudent);
        newmyAdapter = new MyAdapter(getContext(), myitems_new);
        newlistView.setAdapter(newmyAdapter);

        newlistView2 = (ListView)view.findViewById(R.id.list_items_newstudent2);
        newmyAdapter2 = new MyAdapter(getContext(), myitems2_new);
        newlistView2.setAdapter(newmyAdapter2);

        spinner_pass = (Spinner)view.findViewById(R.id.spin_passtype_new);
        List<String> passtype = new ArrayList<String>();
        passtype.add("Ist Class");
        passtype.add("IInd Class");
        ArrayAdapter<String> SpinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, passtype);
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_pass.setAdapter(SpinnerAdapter);

        spin_period_new = (Spinner) view.findViewById(R.id.spin_period_new);
        List<String> new_pass_period = new ArrayList<String>();
        new_pass_period.add("Monthly");
        new_pass_period.add("Quaterly");
        ArrayAdapter<String> SpinnerAdapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, new_pass_period);
        SpinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_period_new.setAdapter(SpinnerAdapter2);

        btnExisting = (Button)view.findViewById(R.id.btnExisting);
        btnNew = (Button)view.findViewById(R.id.btnNew);

        btnExisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserConcessionActivity)getActivity()).setViewPager(0);
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserConcessionActivity)getActivity()).setViewPager(1);
            }
        });

        btnShow = (Button) view.findViewById(R.id.btnshow);
        btnSubmit = (Button) view.findViewById(R.id.btnsubmit);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Admission_no = newmyAdapter.getValueFromEditText(0);
                String roll = newmyAdapter.getValueFromEditText(1);
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
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_station = newmyAdapter2.getValueFromEditText(0);

                if (current_station.equals("")) {
                    Toast.makeText(getActivity(), "Please fill all the details!", Toast.LENGTH_SHORT).show();
                } else {
                    Send objSend = new Send();
                    objSend.execute("");
                }
            }
        });
        return view;

    }

    private void getNewTextView()
    {
        myitems_new.add(new MyItem("Admission Number"));
        myitems_new.add(new MyItem("Roll Number"));
    }

    private void getNewTextView2()
    {
        myitems2_new.add(new MyItem("From Station"));
    }

    private class Send extends AsyncTask<String, String, String> {
        String msg = "";
        String Admission_no = newmyAdapter.getValueFromEditText(0);
        String roll = newmyAdapter.getValueFromEditText(1);
        String current_ticket_from = newmyAdapter2.getValueFromEditText(0);
        String current_ticket_type = spinner_pass.getSelectedItem().toString();
        String current_ticket_period = spin_period_new.getSelectedItem().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mActivity = getActivity();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnectionHelper conStr = new ConnectionHelper();
                connect = conStr.connection();
                if (connect == null) {
                    msg = "Check connection";
                  //  Toast.makeText(getActivity(), "Check Connection", Toast.LENGTH_SHORT).show();
                } else {
                    String query = "{call usp_Insert_New_RailwayConcession(?,?,?,?,?)}";
                    CallableStatement stmt = connect.prepareCall(query);
                    stmt.setString(1, Admission_no);
                    stmt.setString(2,roll);
                    stmt.setString(3,current_ticket_from);
                    stmt.setString(4,current_ticket_type);
                    stmt.setString(5,current_ticket_period);
                    ResultSet rs = stmt.executeQuery();
                    msg = "Successfully inserted data";
                    ConnectionResult = " successful";
                    isSuccess = true;
                    connect.close();
                }
                connect.close();
            } catch (SQLException se) {
                msg = "Database Exception";
                Log.e("error here 1 :  ", se.getMessage());

            } catch (ClassNotFoundException e) {
                msg = "Class Not Found"+e.getMessage();
                Log.e("error here 2 : ", e.getMessage());
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {

            if(isSuccess == true) {
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

            else {
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
                    String query = "select pass_code from dbo.railway_concession where Admission_no =" + "'" + Admission_no + "'" + "and current_station_name =" + "'" +current_ticket_from+ "'";
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
