package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecycledNewStudentsItemsAdapter extends RecyclerView.Adapter<RecycledNewStudentsItemsAdapter.ViewHolder> {

    public Context context;
    private List<RecycledNewStudentItems> values;

    public RecycledNewStudentsItemsAdapter(List<RecycledNewStudentItems> myDataset, Context context) {
        values = myDataset;
        this.context = context;
    }

    @Override
    public RecycledNewStudentsItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recycled_new_students_items, parent, false);
        RecycledNewStudentsItemsAdapter.ViewHolder vh = new RecycledNewStudentsItemsAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycledNewStudentsItemsAdapter.ViewHolder holder, int position) {

        final RecycledNewStudentItems recycledItems = values.get(position);
        holder.rv_stu_id.setText(recycledItems.getStudentID());
        holder.rv_new_name.setText(recycledItems.getNewName());
        holder.rv_phone.setText(recycledItems.getPhone());
        holder.rv_email.setText(recycledItems.getEmail());
        holder.rv_dob.setText(recycledItems.getDob());
        holder.rv_caste.setText(recycledItems.getCaste());
        holder.rv_tenth.setText(recycledItems.getTenth());
        holder.rv_twelth.setText(recycledItems.getTwelth());
        holder.rv_applied_stream.setText(recycledItems.getStreamName());
        holder.rv_stream_year.setText(recycledItems.getStreamYear());


    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView rv_stu_id, rv_new_name, rv_phone, rv_email, rv_dob, rv_caste, rv_tenth, rv_twelth, rv_applied_stream, rv_stream_year;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;

            rv_stu_id = v.findViewById(R.id.rv_stu_id);
            rv_new_name = v.findViewById(R.id.rv_new_name);
            rv_phone = v.findViewById(R.id.rv_phone);
            rv_email = v.findViewById(R.id.rv_email);
            rv_dob = v.findViewById(R.id.rv_dob);
            rv_caste = v.findViewById(R.id.rv_caste);
            rv_tenth = v.findViewById(R.id.rv_tenth);
            rv_twelth = v.findViewById(R.id.rv_twelth);
            rv_applied_stream = v.findViewById(R.id.rv_applied_stream);
            rv_stream_year = v.findViewById(R.id.rv_stream_year);

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int p = getLayoutPosition();
                    System.out.println("LongClick: " + p);
                    return true;
                }
            });

        }

    }
}

