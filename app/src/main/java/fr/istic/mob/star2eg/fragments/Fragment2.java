package fr.istic.mob.star2eg.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.istic.mob.star2eg.MainActivity;
import fr.istic.mob.star2eg.R;
import fr.istic.mob.star2eg.adapters.BusRouteAdapter;
import fr.istic.mob.star2eg.adapters.StopAdapter;
import fr.istic.mob.star2eg.modeles.StarContract;
import fr.istic.mob.star2eg.modeles.Stop;

public class Fragment2 extends Fragment {
    private MainActivity activity ;
    private  List<Stop> listStop ;
    private ListView listView ;
    private TextView line ;
    private TextView direction ;

    public Fragment2(MainActivity activity){
        this.activity  = activity ;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView)getView().findViewById(R.id.listView) ;
        line = (TextView)getView().findViewById(R.id.bus_route) ;
        direction = (TextView)getView().findViewById(R.id.bus_direction) ;


        Map<String,String> data =  this.activity.provideInfoDataToFragment2();

         listStop = getStop(data.get("route_id") , data.get("direction_id"));

        line.setText(data.get("bus_name"));
        direction.setText(data.get("direction"));





        StopAdapter adapter = new StopAdapter(this.activity,
                R.layout.stop_adapter_item,
                R.id.textViewItemNameParent,
                R.id.textView_item_name,
                listStop);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Stop stop = listStop.get(position) ;

                activity.saveInfoFromFragment_2(stop);
                activity.goToNextFragment(2) ;
            }
        });

    }




    private List<Stop> getStop(String busRouteId, String direnctionId){
        List<Stop> listStops = new ArrayList<>() ;
        String[] selectionArgs = {busRouteId, direnctionId};
        Cursor cursor = this.activity.getContentResolver().query(StarContract.Stops.CONTENT_URI, null, null, selectionArgs,StarContract.Stops.StopColumns.STOP_ID);

        if (cursor.moveToFirst()) {
            do {
                Stop item = new Stop(
                        cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns._ID)),
                        cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME)),
                        cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.DESCRIPTION)),
                        cursor.getFloat(cursor.getColumnIndex(StarContract.Stops.StopColumns.LATITUDE)),
                        cursor.getFloat(cursor.getColumnIndex(StarContract.Stops.StopColumns.LONGITUDE)),
                        cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.WHEELCHAIR_BOARDING))

                );
                listStops.add(item);
                //idtrips.add(cursor.getString(cursor.getColumnIndex(StarContract.Trips.TripColumns.TRIP_ID))) ;
            } while (cursor.moveToNext());
        }


        /**
         * Eliminate duplicates
         */
        int lastIndex = listStops.size() ;
        String stop_name = "" ;
        for(int i = 0 ; i<listStops.size();i++ ) {
            if( i == 0){ stop_name =   listStops.get(0).getName() ;  }

            if( (i != 0)&& (listStops.get(i).getName().equals(stop_name)) ){ lastIndex = i ; }

        }

        return  listStops.subList(0,lastIndex) ;
    }





















}