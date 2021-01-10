package fr.istic.mob.star2eg.fragments;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import fr.istic.mob.star2eg.MainActivity;
import fr.istic.mob.star2eg.R;
import fr.istic.mob.star2eg.adapters.BusRouteAdapter;
import fr.istic.mob.star2eg.modeles.BusRoute;
import fr.istic.mob.star2eg.modeles.StarContract;


public class SearchDialog extends Dialog {
    private MainActivity activity ;
    private String stop_name = "" ;
    private ListView listView ;
    private TextView stop_label  ;
    public SearchDialog(@NonNull MainActivity context, String stop_name) {
        super(context);

        this.activity = context ;
        this.stop_name = stop_name ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        setContentView(R.layout.search_dialog);
        listView = (ListView)findViewById(R.id.listView) ;
        stop_label = (TextView)findViewById(R.id.stop_name) ;

        stop_label.setText(stop_name);

        List<BusRoute> listBusRoute =  getBusRoute(stop_name) ;

        BusRouteAdapter adapter = new BusRouteAdapter(this.activity,
                R.layout.bus_route_adapter_item,
                R.id.textViewItemNameParent,
                R.id.textView_item_name,
                R.id.direction1,
                R.id.direction2,
                listBusRoute);

        listView.setAdapter(adapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusRoute busRoute = (BusRoute) listView.getItemAtPosition(position) ;
                activity.setSelectedBusRoute(busRoute);
                dismiss();
            }
        });
    }


    public List<BusRoute> getBusRoute(String stop_name) {
        String[] selectionArgs = {stop_name};
        Cursor cursor = this.activity.getContentResolver().query(StarContract.RoutesForStop.CONTENT_URI, null, null, selectionArgs, StarContract.Stops.StopColumns.STOP_ID);

        List<BusRoute> busRoutes = new ArrayList<>();

        if( cursor != null) {
            while (cursor.moveToNext()) {
                BusRoute item = new BusRoute(
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.ROUTE_ID)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.LONG_NAME)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TYPE)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.COLOR)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR))
                );
                busRoutes.add(item) ;
            }
        }

        int i = busRoutes.size() ;
        if(i != 0){busRoutes.remove(i-1)  ; }

        cursor.close();
        return busRoutes;
    }

}
