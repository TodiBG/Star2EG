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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import fr.istic.mob.star2eg.adapters.BusRouteAdapter;
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
    private String full_time = "" ;
    private Spinner spinner_bus_line  ;
    private Spinner spinner_direction  ;
    private List<BusRoute> listBusRoute ;
    private BusRoute selectedBusRoute ;
    private int directionId = 0 ;
    private List<String>  directionsList;
    private Button next ;
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

        this.next = getView().findViewById(R.id.next);
        this.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.saveInfoFromFragment_1(date.getText().toString(),full_time,selectedBusRoute, directionsList, directionId) ;
                activity.goToNextFragment(1) ;
            }
        });

        date = (EditText)getView().findViewById(R.id.date);
        date.setOnClickListener(datePicker);
        date.setFocusable(false);

        time = (EditText)getView().findViewById(R.id.time);
        time.setOnClickListener(timePicker);
        time.setFocusable(false);


        // Create TimePickerDialog:
        timePickerDialog = new TimePickerDialog(getContext(), timeSetListener, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);

        spinner_bus_line = (Spinner)getView().findViewById(R.id.spinner_line) ;
        spinner_direction = (Spinner)getView().findViewById(R.id.spinner_direction) ;



        this.next.setVisibility(View.INVISIBLE);

        listBusRoute =  getBusRoute() ;
        // Adapter"
        BusRouteAdapter adapter = new BusRouteAdapter(this.activity,
                R.layout.bus_route_adapter_item,
                R.id.textViewItemNameParent,
                R.id.textView_item_name,
                this.listBusRoute);

        this.spinner_bus_line.setAdapter(adapter);

        this.spinner_bus_line.setOnItemSelectedListener(bus_line_listener);
        this.spinner_direction.setOnItemSelectedListener(direction_listener) ;
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

                            if(!time.getText().toString().equals("")){
                                spinner_bus_line.setEnabled(true);
                            }
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    } ;




    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            String  hours = "";
            String min = "" ;

            if(hourOfDay<10 ){ hours = "0"+hourOfDay ;  }
            else { hours = ""+hourOfDay ;   }

            if(minute<10 ){ min = "0"+minute ;}
            else {  min = ""+minute ; }

            full_time = hours+":"+min+":00";

            time.setText(full_time);



            if(!date.getText().toString().equals("")){
                spinner_bus_line.setEnabled(true);
            }
        }
    };



    View.OnClickListener timePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            timePickerDialog.show();
        }
    } ;


    boolean not_execution_line_listener = false ;
   AdapterView.OnItemSelectedListener  bus_line_listener  = new AdapterView.OnItemSelectedListener() {
       @Override
       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

           selectedBusRoute  = listBusRoute.get(position) ;

           directionsList = new ArrayList<>() ;

           String[]splitArray = selectedBusRoute.getLongName().split("<>") ;

           int length = splitArray.length ;

           if( length > 1 ){
               directionsList.add(splitArray[length-1]);
               directionsList.add(splitArray[0]);
           }


           if(not_execution_line_listener) {
               spinner_direction.setEnabled(true);
           }
           not_execution_line_listener = true ;


           ArrayAdapter<String> arrayAdapte  = new ArrayAdapter<>(getActivity(), R.layout.item, directionsList) ;
           arrayAdapte.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
           spinner_direction.setAdapter(arrayAdapte);
       }

       @Override
       public void onNothingSelected(AdapterView<?> parent) {  }
   };

    boolean not_execution_direction_listener = false ;
    AdapterView.OnItemSelectedListener  direction_listener  = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            directionId = position ;
            if( not_execution_direction_listener){
                next.setVisibility(View.VISIBLE);
            }

            not_execution_direction_listener = true ;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {  }
    };




    public  List<BusRoute> getBusRoute() {
        Cursor cursor = this.activity.getContentResolver().query(StarContract.BusRoutes.CONTENT_URI, null, null, null,null);
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