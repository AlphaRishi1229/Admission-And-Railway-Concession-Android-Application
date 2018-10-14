package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecycledItemsAdapter extends RecyclerView.Adapter<RecycledItemsAdapter.ViewHolder> {

    private List<RecycledItems> values;
    public Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView rv_adm, rv_name, rv_roll, rv_stream, rv_sem;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            rv_adm = (TextView) v.findViewById(R.id.rv_adm);
            rv_name = (TextView) v.findViewById(R.id.rv_name);
            rv_roll = (TextView) v.findViewById(R.id.rv_roll);
            rv_stream = (TextView) v.findViewById(R.id.rv_stream);
            rv_sem = (TextView) v.findViewById(R.id.rv_sem);

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int p = getLayoutPosition();
                    System.out.println("LongClick: "+p);
                    return true;
                }
            });
        }
    }

    public RecycledItemsAdapter(List<RecycledItems> myDataset, Context context) {
        values = myDataset;
        this.context = context;
    }

    @Override
    public RecycledItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recycled_student_items, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final RecycledItems recycledItems = values.get(position);
        holder.rv_adm.setText(recycledItems.getAdmNo());
        holder.rv_name.setText(recycledItems.getName());
        holder.rv_roll.setText(recycledItems.getRoll());
        holder.rv_stream.setText(recycledItems.getStream());
        holder.rv_sem.setText(recycledItems.getSem());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
