package com.example.kurokainos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DegalinesAdaptor extends ArrayAdapter<Degalines> {

private Context mContext;
private int mResource;
    public DegalinesAdaptor(@NonNull Context context, int resource, @NonNull ArrayList<Degalines> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource,parent,false);

        TextView degalinesPavadinimas = convertView.findViewById(R.id.degalinesPavadinimas);

        TextView degalinesAdresas = convertView.findViewById(R.id.degalinesAdresas);

        degalinesPavadinimas.setText(getItem(position).getPavadinimas());

        degalinesAdresas.setText(getItem(position).getAdresas());




        return convertView;
    }
}
