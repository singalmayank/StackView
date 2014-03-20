package com.example.stack;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StackAdapter extends ArrayAdapter<WidgetItem> {
	 
    private ArrayList<WidgetItem> items;
    private Context ctx;
 
    public StackAdapter(Context context, int textViewResourceId,
            ArrayList<WidgetItem> objects) {
        super(context, textViewResourceId, objects);
 
        this.items = objects;
        this.ctx = context;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.widget_item, null);
        }
 
        WidgetItem m = items.get(position);
 
        if (m != null) {
                TextView text = (TextView) v.findViewById(R.id.widget_item);
 
                if (text != null) {
                    text.setText(m.text);
                    }
        }
        return v;
    }
}
