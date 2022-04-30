package com.example.kurokainos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kurokainos.R;

import java.util.ArrayList;

public class DegalinesCommentListAdapter extends ArrayAdapter<DegalinesCommentList> {

    private final Context mContext;
    private final int mResource;

    public DegalinesCommentListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DegalinesCommentList> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource,parent,false);





        TextView benzinas = convertView.findViewById(R.id.benzinoKaina);

        TextView dyzelis = convertView.findViewById(R.id.dyzelinoKaina);

        TextView dujos = convertView.findViewById(R.id.dujuKaina);

        TextView data = convertView.findViewById(R.id.commentData);




        benzinas.setText( getItem(position).getBenzinoKaina());

        dyzelis.setText( getItem(position).getDyzelioKaina());

        dujos.setText( getItem(position).getDujuKaina());

        data.setText( getItem(position).getCommentDate());




        return convertView;
    }

}
