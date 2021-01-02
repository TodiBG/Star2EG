package fr.istic.mob.star2eg.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import fr.istic.mob.star2eg.MainActivity;
import fr.istic.mob.star2eg.R;
import java.util.Calendar ;

public class Fragment1 extends Fragment {

    private EditText date ;
    private DatePickerDialog datePickerDialog;
    private EditText time ;
    private TimePickerDialog timePickerDialog ;
    private int hours = 0 ;
    private int minutes = 0 ;


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
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction() ;
                fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.frameLayout, MainActivity.getFragment(1)) ;
                fragmentTransaction.commit() ;
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





}