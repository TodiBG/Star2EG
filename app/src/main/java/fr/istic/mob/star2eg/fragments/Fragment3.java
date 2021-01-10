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
import fr.istic.mob.star2eg.adapters.StopAdapter;
import fr.istic.mob.star2eg.adapters.StopTimeAdapter;
import fr.istic.mob.star2eg.modeles.StarContract;
import fr.istic.mob.star2eg.modeles.Stop;
import fr.istic.mob.star2eg.modeles.StopTime;

public class Fragment3 extends Fragment {

    private MainActivity activity ;
    private List<StopTime> stopTimeList ;
    private  ListView listView ;
    private TextView line ;
    private TextView direction ;
    private  TextView stopTextView ;

    public Fragment3(MainActivity activity){
        this.activity  = activity ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_3, container, false);
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


       stopTimeList = getStopTimes();

        StopTimeAdapter adapter = new StopTimeAdapter(this.activity,
                R.layout.stop_adapter_item,
                R.id.textViewItemNameParent,
                R.id.textView_item_name,
                stopTimeList);

        listView.setAdapter(adapter);

        Map<String,String> data =  this.activity.provideInfoDataToFragment3();
        line.setText(data.get("bus_name"));
        direction.setText(data.get("direction"));
        stopTextView.setText(data.get("stop"));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StopTime stopTime = stopTimeList.get(position) ;

                activity.saveInfoFromFragment_3(stopTime);
               activity.goToNextFragment(3) ;
            }
        });

    }



     private  List<StopTime>  getStopTimes(){


        Map<String,String> data =  this.activity.provideInfoDataToFragment3();

        // Log.i("TEST","route_id : "+data.get("route_id")+", stop_id : "+data.get("stop_id")+", direction_id : "+data.get("direction_id")+", day : "+data.get("day")+", arrival_time : "+data.get("arrival_time") ) ;


         String[] selectionArgs = {data.get("route_id"),data.get("stop_id"),data.get("direction_id"),data.get("day"),data.get("arrival_time") };

        Cursor cursor = this.activity.getContentResolver().query(StarContract.StopTimes.CONTENT_URI, null, null, selectionArgs,StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME);

         List<StopTime> stopTimes = new ArrayList<>() ;


         if( cursor != null){

             if (cursor.moveToFirst()){


                do {
                     StopTime item = new StopTime(
                             cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.TRIP_ID)),
                             cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)),
                             cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME)),
                             cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.STOP_ID)),
                             cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE))
                     );

                     stopTimes.add(item);
                 } while (cursor.moveToNext());
             }
         }


         //Log.i("TEST","stopTimes : "+stopTimes.size() ) ;


        return  stopTimes ;
     }









}