package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AdminDatabaseFragment extends Fragment {

    private ArrayList<RecycledItems> itemsArrayList;
    private RecycledItemsAdapter myAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    Connection connect;
    String ConnectionResult;
    Boolean isSuccess;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_admin_database_fragment, container, false);

        recyclerView = view.findViewById(R.id.stu_recycle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        itemsArrayList = new ArrayList<RecycledItems>();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                final String AdmNo = ((TextView)recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.rv_stu_id)).getText().toString();
                final String Name = ((TextView)recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.rv_new_name)).getText().toString();
                final String roll = ((TextView)recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.rv_phone)).getText().toString();
                String Stream = ((TextView)recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.rv_email)).getText().toString();
                String Sem = ((TextView)recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.rv_sem)).getText().toString();

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(AdmNo+"::"+Name);
                builder.setMessage(roll+"\n"+Stream+"\n"+Sem+"Do you want to edit details of this student?");
                builder.setPositiveButton(
                        "EDIT",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               /* Fragment fr=new AdminDatabaseFragment();
                                FragmentManager fm=getFragmentManager();
                                FragmentTransaction ft=fm.beginTransaction();
                                Bundle args = new Bundle();
                                args.putString("AdmNo", AdmNo);
                                fr.setArguments(args);
                               // ft.replace(R.id.con, fr);
                                ft.commit();

                                /*FragmentManager fragmentManager;

                                Bundle arg = new Bundle();
                                arg.putString("AdmNo",AdmNo);

                                AdminDatabaseFragment fragment = new AdminDatabaseFragment();
                                fragment.setArguments(arg);

                                fragmentManager.beginTransaction().add("Data1",fragment,fragment.getClass().getName())
                                        .addToBackStack(null)
                                        .commit();*/
                            }
                        });
                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        }));
        SyncData studentData = new SyncData();
        studentData.execute("");
        return view;
    }

    private class SyncData extends AsyncTask<String, String, String> {
        String msg = "";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(getActivity(), "Synchronising",
                    "Database Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                ConnectionHelper conStr = new ConnectionHelper();
                connect = conStr.connection();        // Connect to database
                if (connect == null) {
                    ConnectionResult = "Check Your Internet Access!";
                } else {
                    String query = "{call usp_GetAdmittedStudentDetails()}";
                    CallableStatement stmt = connect.prepareCall(query);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String Adm_no = rs.getString("ADMISSION_NO");
                        String name = rs.getString("STUDENT_NAME");
                        String stream = rs.getString("STREAM_NAME");
                        String roll = rs.getString("ROLL_NO");
                        String sem = rs.getString("STREAMYEAR");

                        itemsArrayList.add(new RecycledItems(Adm_no, name, roll, stream, sem));
                    }
                    ConnectionResult = " successful";
                    isSuccess = true;
                    connect.close();
                }
            } catch (Exception ex) {
                isSuccess = false;
                ConnectionResult = ex.getMessage();
                Toast.makeText(getActivity(), ConnectionResult, Toast.LENGTH_LONG).show();
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            progress.dismiss();
            if (isSuccess == false) {
            } else {
                try {
                    myAdapter = new RecycledItemsAdapter(itemsArrayList, getActivity());
                    recyclerView.setAdapter(myAdapter);
                } catch (Exception ex) {

                }
            }
        }
    }
}