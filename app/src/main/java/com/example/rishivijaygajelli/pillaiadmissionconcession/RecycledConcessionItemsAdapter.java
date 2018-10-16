package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecycledConcessionItemsAdapter extends RecyclerView.Adapter<RecycledConcessionItemsAdapter.ViewHolder> implements Filterable {

    private List<ConcessionRecycledItems> values;
    private List<ConcessionRecycledItems> values_full;

    public Context context;
    private Filter valuesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ConcessionRecycledItems> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(values_full);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ConcessionRecycledItems item : values_full) {
                    if (item.getPass_code().toLowerCase().contains(filterPattern)
                            || item.getAdmissionNo().toLowerCase().contains(filterPattern)
                            || item.getRollNo().toLowerCase().contains(filterPattern)
                            || item.getPassPeriod().toLowerCase().contains(filterPattern)
                            || item.getPassType().toLowerCase().contains(filterPattern)
                            || item.getStationName().toLowerCase().contains(filterPattern) ) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            values.clear();
            values.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public RecycledConcessionItemsAdapter(List<ConcessionRecycledItems> myDataset,Context context)
    {
        values = myDataset;
        values_full = new ArrayList<>(myDataset);
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

        holder.last_vch_no.setText(concessionRecycledItems.getLast_vch_no());
        holder.ticket_no.setText(concessionRecycledItems.getLast_ticket_no());
        holder.last_station_name.setText(concessionRecycledItems.getLast_station_name());
        holder.last_ticket_period.setText(concessionRecycledItems.getLast_ticket_period());
        holder.last_issue_date.setText(concessionRecycledItems.getLast_issue_date());
        holder.last_pass_type.setText(concessionRecycledItems.getLast_pass_type());


    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    @Override
    public Filter getFilter() {
        return valuesFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView rv_pass_code, rv_adm, rv_roll, rv_station_name, rv_pass_type, rv_pass_period;
        public TextView last_vch_no, ticket_no, last_station_name, last_ticket_period, last_issue_date, last_pass_type;
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

            last_vch_no = v.findViewById(R.id.last_vch_no);
            ticket_no = v.findViewById(R.id.ticket_no);
            last_station_name = v.findViewById(R.id.last_station_name);
            last_ticket_period = v.findViewById(R.id.last_ticket_period);
            last_issue_date = v.findViewById(R.id.last_issue_date);
            last_pass_type = v.findViewById(R.id.last_pass_type);

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

}
