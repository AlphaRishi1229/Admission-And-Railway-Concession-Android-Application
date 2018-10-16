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

public class RecycledItemsAdapter extends RecyclerView.Adapter<RecycledItemsAdapter.ViewHolder> implements Filterable{

    private List<RecycledItems> values;
    private List<RecycledItems> values_full;
    public Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView rv_adm, rv_name, rv_roll, rv_stream, rv_sem;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            rv_adm = v.findViewById(R.id.rv_stu_id);
            rv_name = v.findViewById(R.id.rv_new_name);
            rv_roll = v.findViewById(R.id.rv_phone);
            rv_stream = v.findViewById(R.id.rv_email);
            rv_sem = v.findViewById(R.id.rv_sem);

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
    private Filter valuesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RecycledItems> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(values_full);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (RecycledItems item : values_full) {
                    if (item.getAdmNo().toLowerCase().contains(filterPattern)
                            ||item.getName().toLowerCase().contains(filterPattern)
                            || item.getRoll().toLowerCase().contains(filterPattern)
                            || item.getStream().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
         //   results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            values.clear();
            values.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

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

    public RecycledItemsAdapter(List<RecycledItems> myDataset, Context context) {
        values = myDataset;
        this.context = context;
        values_full = new ArrayList<>(myDataset);

    }

    @Override
    public Filter getFilter() {
        return valuesFilter;
    }
}


