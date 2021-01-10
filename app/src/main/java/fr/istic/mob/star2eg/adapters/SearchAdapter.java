package fr.istic.mob.star2eg.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.istic.mob.star2eg.modeles.RouteDetail;
import fr.istic.mob.star2eg.modeles.Stop;

public class SearchAdapter extends BaseAdapter {

    private LayoutInflater flater;
    private List<String> listStops;
    private int listItemLayoutResource;
    private int textViewItemShortName;
    //private int textViewItemShortHour ;


    public SearchAdapter(Activity context, int listItemLayoutResource, int textViewItemName,List<String> list) {
        this.listItemLayoutResource = listItemLayoutResource;
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
        return  0   ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String stop = (String) getItem(position);


        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);

        TextView textViewItemName = (TextView) rowView.findViewById(this.textViewItemShortName);

        textViewItemName.setText(stop);

        //TextView textViewItemHour = (TextView) rowView.findViewById(this.textViewItemShortHour);

       // textViewItemHour.setText(routeDetail.getHour());



        return rowView;
    }
}






























