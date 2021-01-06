package fr.istic.mob.star2eg.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.istic.mob.star2eg.MainActivity;
import fr.istic.mob.star2eg.R;
import fr.istic.mob.star2eg.adapters.RouteDetailAdapter;
import fr.istic.mob.star2eg.adapters.StopTimeAdapter;
import fr.istic.mob.star2eg.modeles.RouteDetail;
import fr.istic.mob.star2eg.modeles.StarContract;
import fr.istic.mob.star2eg.modeles.StopTime;

public class Fragment4 extends Fragment {

    private MainActivity activity ;
    private List<RouteDetail> stopTimeList ;
    private  ListView listView ;
    private TextView line ;
    private TextView direction ;
    private  TextView stopTextView ;

    public Fragment4(MainActivity activity){
        this.activity  = activity ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_4, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        getView().findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.goToPreviousFragment(1);
            }
        });

        line = (TextView)getView().findViewById(R.id.bus_route) ;
        direction = (TextView)getView().findViewById(R.id.bus_direction) ;
        stopTextView = (TextView)getView().findViewById(R.id.stop) ;
        listView = (ListView)getView().findViewById(R.id.listView) ;


       stopTimeList = getRouteDetails();

        RouteDetailAdapter adapter = new RouteDetailAdapter(this.activity,
                R.layout.route_detail_adapter_item,
                R.id.textView_item_name,
                R.id.textView_item_hour,
                stopTimeList);

        listView.setAdapter(adapter);

        Map<String,String> data =  this.activity.provideInfoDataToFragment4();

        line.setText(data.get("bus_name"));
        direction.setText(data.get("direction"));
        stopTextView.setText(data.get("stop"));


    }



     private  List<RouteDetail>  getRouteDetails(){


        Map<String,String> data =  this.activity.provideInfoDataToFragment4();

        String[] selectionArgs = {data.get("trip_id"),data.get("hour") };

         Log.i("Test","trip_id : "+data.get("trip_id")) ;
         Log.i("Test","hour : "+data.get("hour")) ;



         Cursor cursor = this.activity.getContentResolver().query(StarContract.RouteDetails.CONTENT_URI, null, null, selectionArgs,null);

         List<RouteDetail> routeDetails = new ArrayList<>() ;


         if( cursor != null){ }

             if (cursor.moveToFirst()){
                do {
                    RouteDetail item = new RouteDetail(
                             cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME)),
                             cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME))
                     );

                    routeDetails.add(item);
                 } while (cursor.moveToNext());

             }



        return  routeDetails ;
     }









}