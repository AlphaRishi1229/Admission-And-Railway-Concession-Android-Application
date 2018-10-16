package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AdmissionFormsFragment extends Fragment {

    Connection connect;
    String ConnectionResult;
    Boolean isSuccess;
    private ArrayList<RecycledNewStudentItems> itemsArrayList;
    private RecycledNewStudentsItemsAdapter myAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_admission_forms, container, false);

        recyclerView = view.findViewById(R.id.new_stu_recycle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        itemsArrayList = new ArrayList<RecycledNewStudentItems>();

        searchView = view.findViewById(R.id.search_view_newstu);
        searchView.setQueryHint("Enter value to be searched");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });

        NewStudentSync ns = new NewStudentSync();
        ns.execute();

        return view;
    }

    public class NewStudentSync extends AsyncTask<String,String,String>
    {
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
                    String query = "{call usp_GetAppliedStudentDetails()}";
                    CallableStatement stmt = connect.prepareCall(query);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String stu_id = rs.getString("NewStudentID");
                        String name = rs.getString("NewStudentName");
                        String phoneNo = rs.getString("PhoneNo");
                        String emailId = rs.getString("EmailId");
                        String dateOfBirth = rs.getString("DateOfBirth");
                        String caste = rs.getString("Caste");
                        String tenthPercent = rs.getString("TenthPercent");
                        String twelthPercent = rs.getString("TwelthPercent");
                        String stream_name = rs.getString("STREAM_NAME");
                        String streamyear = rs.getString("STREAMYEAR");

                        itemsArrayList.add(new RecycledNewStudentItems(stu_id, name, phoneNo, emailId, dateOfBirth,
                                caste,tenthPercent,twelthPercent,stream_name,streamyear));
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
                    myAdapter = new RecycledNewStudentsItemsAdapter(itemsArrayList, getActivity());
                    recyclerView.setAdapter(myAdapter);
                } catch (Exception ex) {

                }
            }
        }
    }
}
