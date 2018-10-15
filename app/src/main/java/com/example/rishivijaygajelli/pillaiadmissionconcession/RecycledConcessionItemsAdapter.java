package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecycledConcessionItemsAdapter extends RecyclerView.Adapter<RecycledConcessionItemsAdapter.ViewHolder> {

    private List<ConcessionRecycledItems> values;
    public Context context;

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView rv_pass_code, rv_adm, rv_roll, rv_station_name, rv_pass_type, rv_pass_period;
        public View layout;

        public ViewHolder(View v)
        {
            super(v);
            layout = v;
            rv_pass_code = v.findViewById(R.id.rv_pass_code);
            rv_adm = v.findViewById(R.id.rv_stu_id);
            rv_roll = v.findViewById(R.id.rv_phone);
            rv_station_name = v.findViewById(R.id.rv_station_name);
            rv_pass_type = v.findViewById(R.id.rv_pass_type);
            rv_pass_period = v.findViewById(R.id.rv_pass_period);

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

    public RecycledConcessionItemsAdapter(List<ConcessionRecycledItems> myDataset,Context context)
    {
        values = myDataset;
        this.context = context;
    }
    @Override
    public RecycledConcessionItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recycled_concession_items, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConcessionRecycledItems concessionRecycledItems = values.get(position);
        holder.rv_pass_code.setText(concessionRecycledItems.getPass_code());
        holder.rv_adm.setText(concessionRecycledItems.getAdmissionNo());
        holder.rv_roll.setText(concessionRecycledItems.getRollNo());
        holder.rv_station_name.setText(concessionRecycledItems.getStationName());
        holder.rv_pass_type.setText(concessionRecycledItems.getPassType());
        holder.rv_pass_period.setText(concessionRecycledItems.getPassPeriod());

    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
