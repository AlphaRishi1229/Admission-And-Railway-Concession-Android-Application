package com.example.rishivijaygajelli.pillaiadmissionconcession;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class MyAdapter extends BaseAdapter {

    private ArrayList<MyItem> listItems;
    private Context context;
    private HashMap<String,String> edittextvalues = new HashMap<String, String>();

    public MyAdapter(Context context, ArrayList<MyItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }


    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public MyItem getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        View row;
        boolean viewisnull = false;
        ListViewHolder listViewHolder;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.my_adapter, parent, false);
            listViewHolder = new ListViewHolder();
            listViewHolder.tvname = row.findViewById(R.id.tvname);
            viewisnull = true;
            row.setTag(listViewHolder);
        }
        else
            {
            row = view;
            listViewHolder = (ListViewHolder) row.getTag();
        }

        TextInputEditText etText = (TextInputEditText) row.findViewById(R.id.etText);

        if(viewisnull)
        {
            etText.addTextChangedListener(new GenericTextWatcher(etText));
        }
        etText.setTag("thenextedittext"+position);
        MyItem myItem = getItem(position);
        String text = myItem.TextTitle;
        listViewHolder.tvname.setHint(text);
        return row;
    }

    private class GenericTextWatcher implements TextWatcher
    {
        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable) {

            String text = editable.toString();
            MyAdapter.this.edittextvalues.put((String) view.getTag(), editable.toString());
        }
    }

    public String getValueFromEditText(int position){
        //here you need to recreate the id for the first editText
        String result = edittextvalues.get("thenextedittext"+position);
        if(result ==null)
            result = "";

        return result;
    }

}




