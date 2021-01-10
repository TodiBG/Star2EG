package fr.istic.mob.star2eg.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    private TextView line_label ;
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

                boolean everything_is_ok = true ;

                if( date.getText().toString().equals("") || time.getText().toString().equals("") ){
                    everything_is_ok = false ;
                    Toast.makeText(getContext(), R.string.select_date_and_time, Toast.LENGTH_SHORT).show() ;
                }

                if( spinner_bus_line.getSelectedItemPosition() == 0 ){
                    everything_is_ok = false ;
                    Toast.makeText(getContext(), R.string.select_bus, Toast.LENGTH_SHORT).show() ;
                }

                if( everything_is_ok ){
                    activity.saveInfoFromFragment_1(date.getText().toString(),full_time,selectedBusRoute, directionsList, directionId) ;
                    activity.goToNextFragment(1) ;
                }



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
        line_label = (TextView) getView().findViewById(R.id.line) ;



        listBusRoute =  getBusRoute() ;
        // Adapter"
        BusRouteAdapter adapter = new BusRouteAdapter(this.activity,
                R.layout.bus_route_adapter_item,
                R.layout.bus_route_adapter_item2,
                R.id.textViewItemNameParent,
                R.id.textView_item_name,
                R.id.direction1,
                R.id.direction2,
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


        }
    };


    public void displaySelectedBusName(BusRoute bus){
        line_label.setText(bus.getShortName());

        int b = 0 ;
        for(int i =0 ; i< listBusRoute.size(); i++){
            if(listBusRoute.get(i).getId().equals(bus.getId())){
                b = i ;
                break;
            }
        }

        spinner_bus_line.setSelection(b);

    }



    View.OnClickListener timePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            timePickerDialog.show();
        }
    } ;


   AdapterView.OnItemSelectedListener  bus_line_listener  = new AdapterView.OnItemSelectedListener() {
       @Override
       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

           selectedBusRoute  = listBusRoute.get(position) ;
           line_label.setText(selectedBusRoute.getShortName());

           directionsList = new ArrayList<>() ;

           String[]splitArray = selectedBusRoute.getLongName().split("<>") ;

           int length = splitArray.length ;

           if( length > 1 ){
               directionsList.add(splitArray[length-1]);
               directionsList.add(splitArray[0]);


               ArrayAdapter<String> arrayAdapte  = new ArrayAdapter<>(getActivity(), R.layout.item, directionsList) ;
               arrayAdapte.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
               spinner_direction.setAdapter(arrayAdapte);

           }else {
               directionsList = new ArrayList<>() ;
               ArrayAdapter<String> arrayAdapte  = new ArrayAdapter<>(getActivity(), R.layout.item, directionsList) ;
               arrayAdapte.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
               spinner_direction.setAdapter(arrayAdapte);
           }

       }

       @Override
       public void onNothingSelected(AdapterView<?> parent) {  }
   };


    AdapterView.OnItemSelectedListener  direction_listener  = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            directionId = position ;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {  }
    };




    public  List<BusRoute> getBusRoute() {
        Cursor cursor = this.activity.getContentResolver().query(StarContract.BusRoutes.CONTENT_URI, null, null, null,null);
        List<BusRoute> busRoutes = new ArrayList<>();

        busRoutes.add(new BusRoute("","","","","","","")) ;


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



    public List<String>  searchStop(String searchText){
        searchText = searchText.trim() ;
        List<String> listStops = new ArrayList<>();
        if (!searchText.equals("")) {

            String[] selectionArgs = {searchText};
            Cursor cursor = this.activity.getContentResolver().query(StarContract.SearchedStops.CONTENT_URI, null, null, selectionArgs, StarContract.Stops.StopColumns.STOP_ID);

            if (cursor.moveToFirst()) {
                do {
                        listStops.add(cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME)) );
                } while (cursor.moveToNext());
            }

        }


        return listStops ;
    }





}