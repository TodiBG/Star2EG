package fr.istic.mob.star2eg.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.istic.mob.star2eg.modeles.Stop;
import fr.istic.mob.star2eg.modeles.StopTime;

public class StopTimeAdapter extends BaseAdapter {

    private LayoutInflater flater;
    private List<StopTime> listStops;
    private int listItemLayoutResource;
    private int textViewItemShortName;
    private  int textViewItemNameParent ;


    public StopTimeAdapter(Activity context, int listItemLayoutResource, int textViewItemNameParent, int textViewItemName, List<StopTime> list) {
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
        StopTime stopTime = (StopTime) this.getItem(position);
        return  0 ; //Integer.parseInt(stopTime.getId())   ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StopTime stop_time = (StopTime) getItem(position);


        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);

        TextView textViewItemName = (TextView) rowView.findViewById(this.textViewItemShortName);

        textViewItemName.setText(stop_time.getArrivalTime());

        return rowView;
    }
}
