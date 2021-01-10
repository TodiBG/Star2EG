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
    private int direction1 = 0;
    private int direction2 = 0 ;


    public BusRouteAdapter(Activity context, int listItemLayoutResource, int textViewItemNameParent, int textViewItemName, int direction1 , int direction2 ,List<BusRoute> list) {
        this.listItemLayoutResource = listItemLayoutResource;
        this.textViewItemNameParent = textViewItemNameParent ;
        this.textViewItemShortName = textViewItemName;
        this.direction1 = direction1 ;
        this.direction2 = direction2 ;
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
        return  Integer.parseInt(busRoute.getId())   ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BusRoute busRoute = (BusRoute) getItem(position);


        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);

        TextView textViewItemName = (TextView) rowView.findViewById(this.textViewItemShortName);

        LinearLayout linearLayout = (LinearLayout) rowView.findViewById(this.textViewItemNameParent);

        textViewItemName.setText(busRoute.getShortName());
        textViewItemName.setTextColor(Color.parseColor("#"+busRoute.getTextColor()));

        linearLayout.setBackgroundColor(Color.parseColor("#"+busRoute.getColor()));

        TextView dir1 = (TextView) rowView.findViewById(this.direction1);
        TextView dir2 = (TextView) rowView.findViewById(this.direction2);


        String[]dirs = busRoute.getLongName().split("<>") ;

        if(dirs.length> 0){
            dir1.setText(dirs[0]);
        }

        if(dirs.length> 1){
            dir2.setText(dirs[dirs.length-1] );
        }

        return rowView;
    }
}
