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

public class BusRouteAdapter extends BaseAdapter {

    private LayoutInflater flater;
    private List<BusRoute> listBusRoutes;
    private int listItemLayoutResource;
    private int textViewItemShortName;
    private  int textViewItemNameParent ;


    public BusRouteAdapter(Activity context, int listItemLayoutResource, int textViewItemNameParent, int textViewItemName,List<BusRoute> list) {
        this.listItemLayoutResource = listItemLayoutResource;
        this.textViewItemNameParent = textViewItemNameParent ;
        this.textViewItemShortName = textViewItemName;
        this.listBusRoutes = list;
        this.flater = context.getLayoutInflater();
    }



    @Override
    public int getCount() {
        if(this.listBusRoutes == null)  {
            return 0;
         }
        return this.listBusRoutes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listBusRoutes.get(position);
    }

    @Override
    public long getItemId(int position) {
        BusRoute busRoute = (BusRoute) this.getItem(position);
        return  Integer.parseInt(busRoute.getRoute_id())   ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BusRoute busRoute = (BusRoute) getItem(position);


        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);

        TextView textViewItemName = (TextView) rowView.findViewById(this.textViewItemShortName);

        LinearLayout linearLayout = (LinearLayout) rowView.findViewById(this.textViewItemNameParent); ;

        textViewItemName.setText(busRoute.getShortName());
        textViewItemName.setTextColor(Color.parseColor("#"+busRoute.getTextColor()));

        linearLayout.setBackgroundColor(Color.parseColor("#"+busRoute.getColor()));


        return rowView;
    }
}
