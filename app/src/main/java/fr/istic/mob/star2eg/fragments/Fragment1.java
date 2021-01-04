package fr.istic.mob.star2eg.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import fr.istic.mob.star2eg.MainActivity;
import fr.istic.mob.star2eg.R;
import fr.istic.mob.star2eg.modeles.BusRoute;
import fr.istic.mob.star2eg.modeles.StarContract;

import java.util.ArrayList;
import java.util.Calendar ;
import java.util.List;

public class Fragment1 extends Fragment {
    private MainActivity activity ;
    private EditText date ;
    private DatePickerDialog datePickerDialog;
    private EditText time ;
    private TimePickerDialog timePickerDialog ;
    private int hours = 0 ;
    private int minutes = 0 ;
    private Spinner spinner  ;
    private Spinner spinner1  ;
    private BusRoute[] listBusRoute ;
    List<String> bus ;
    public Fragment1(MainActivity activity){
        this.activity  = activity ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { activity.goToNextFragment(1) ; }
        });

        date = (EditText)getView().findViewById(R.id.date);
        date.setOnClickListener(datePicker);
        date.setFocusable(false);

        time = (EditText)getView().findViewById(R.id.time);
        time.setOnClickListener(timePicker);
        time.setFocusable(false);


        // Create TimePickerDialog:
        timePickerDialog = new TimePickerDialog(getContext(), timeSetListener, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);

        spinner1 = (Spinner)getView().findViewById(R.id.spinner1) ;
        spinner = (Spinner)getView().findViewById(R.id.spinner) ;

        listBusRoute =  getBusRoute() ;

        /*
        bus.add( getBusRoute().get(0).getLongName() ) ;
        bus.add(getBusRoute().get(1).getLongName()) ;
        bus.add(getBusRoute().get(2).getLongName()) ;

         */




        ArrayAdapter<BusRoute> arrayAdapte  = new ArrayAdapter<>(getContext(), R.layout.item, listBusRoute) ;
        arrayAdapte.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinner.setAdapter(arrayAdapte);


        /*ArrayAdapter<String> arrayAdapteBus = new ArrayAdapter<>(getContext(), R.layout.item, bus) ;
        arrayAdapteBus.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinner1.setAdapter(arrayAdapteBus);

         */
    }


    View.OnClickListener datePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // calender class's instance and get current date , month and year from calender
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            datePickerDialog = new DatePickerDialog (getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            date.setText(dayOfMonth + "/"  + (monthOfYear + 1) + "/" + year);
                            date.setFocusable(false);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    } ;




    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time.setText(hourOfDay + ":" + minute );
            hours = hourOfDay;
            minutes = minute;
        }
    };

    View.OnClickListener timePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            timePickerDialog.show();
        }
    } ;



    public  BusRoute[] getBusRoute() {
        Cursor cursor = this.activity.getContentResolver().query(StarContract.BusRoutes.CONTENT_URI, null, null, null,null);
        BusRoute[] busRoutes = new BusRoute[cursor.getCount()];

        int i = 0 ;

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
                busRoutes[i] = item ;

              i++ ;
            }
        }

        System.out.println("-------------- result contains "+i+" ligne - ----------------------");
        cursor.close();
        return busRoutes;
    }
















}