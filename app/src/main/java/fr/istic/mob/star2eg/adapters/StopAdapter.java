package fr.istic.mob.star2eg.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fr.istic.mob.star2eg.modeles.BusRoute;
import fr.istic.mob.star2eg.modeles.Stop;

public class StopAdapter extends BaseAdapter {

    private LayoutInflater flater;
    private List<Stop> listStops;
    private int listItemLayoutResource;
    private int textViewItemShortName;
    private  int textViewItemNameParent ;


    public StopAdapter(Activity context, int listItemLayoutResource, int textViewItemNameParent, int textViewItemName, List<Stop> list) {
        this.listItemLayoutResource = listItemLayoutResource;
        this.textViewItemNameParent = textViewItemNameParent ;
        this.textViewItemShortName = textViewItemName;
        this.listStops = list;
        this.flater = context.getLayoutInflater();
    }



    @Override
    public int getCount() {
        if(this.listStops == null)  {
            return 0;
         }
        return this.listStops.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listStops.get(position);
    }

    @Override
    public long getItemId(int position) {
        Stop stop = (Stop) this.getItem(position);
        return  Integer.parseInt(stop.getId())   ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Stop stop = (Stop) getItem(position);


        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);

        TextView textViewItemName = (TextView) rowView.findViewById(this.textViewItemShortName);

        textViewItemName.setText(stop.getName());

        return rowView;
    }
}
