package fr.istic.mob.star2eg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.istic.mob.star2eg.fragments.Fragment1;
import fr.istic.mob.star2eg.fragments.Fragment2;
import fr.istic.mob.star2eg.fragments.Fragment3;
import fr.istic.mob.star2eg.modeles.BusRoute;
import fr.istic.mob.star2eg.modeles.StarContract;
import fr.istic.mob.star2eg.modeles.Stop;
import fr.istic.mob.star2eg.modeles.StopTime;

public class MainActivity extends AppCompatActivity {

    private static List<Fragment> fragmentsList= new ArrayList<>() ;
    private static int NB_FRAGMENTS = 3 ;


    private String selectedDate ;
    private String full_time ;
    private BusRoute selectedBusRoute ;
    private List<String>  directionsList;
    private int directionId = 0 ;
    private Stop selectedStop ;
    private StopTime selectedStopTime ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentsList.add(new Fragment1(this));
        fragmentsList.add(new Fragment2(this));
        fragmentsList.add(new Fragment3(this));

        selectFragment(fragmentsList.get(0));




        /**
         *
         *
         android:readPermission="stareg.permission.READ_STAR_PROVIDER"
         android:writePermission="stareg.permission.WRITE_STAR_PROVIDER"
         *
         *
         */

    }


    /**
     * Gets informations from Fragment1 and share them with other Fragments
     * @param selectedDate, selected date  in Fragment1
     * @param time, time selected in Fragment1
     * @param selectedBusRoute , BusRoute selected in Fragment1
     * @param directionsList, List of directions for the BusRoute selected in Fragment1
     * @param directionId, Id of direction selected in Fragment1
     */
    public void saveInfoFromFragment_1(String selectedDate, String time, BusRoute selectedBusRoute, List<String> directionsList, int directionId){

        this.selectedDate  = selectedDate;
        this.full_time = time;
        this.selectedBusRoute = selectedBusRoute;
        this.directionsList = directionsList;
        this.directionId = directionId ;


        Log.i("Info","Info from Fragment1  : date = "+selectedDate+" , Time = "+time+" , bus =  "+selectedBusRoute.getShortName()+", direction =  "+directionsList.get(directionId));

    }

    /**
     * Gets informations from Fragment2 and share them with other Fragments
     * @param stop, selected stop in Fragment2
     */
    public void saveInfoFromFragment_2(Stop stop){
        this.selectedStop  = stop;
    }



    public Map<String,String> provideInfoDataToFragment2(){
        Map<String,String> data = new HashMap<>();
        data.put("route_id",selectedBusRoute.getId()) ;
        data.put("bus_name",selectedBusRoute.getShortName()) ;
        data.put("direction",""+ directionsList.get(directionId) ) ;
        data.put("direction_id",""+directionId) ;

        return data ;
    }


    public Map<String,String> provideInfoDataToFragment3(){

        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
        Date dt1= null;
        try { dt1 = format1.parse(selectedDate); } catch (ParseException e) { e.printStackTrace();}

        DateFormat format2=new SimpleDateFormat("EEEE", Locale.US);

        String finalDay = format2.format(dt1);

        Map<String,String> data = provideInfoDataToFragment2() ;

        data.put("stop_id",selectedStop.getId()) ;
        data.put("stop",selectedStop.getName()) ;
        data.put("day",""+finalDay) ;
        data.put("arrival_time",""+full_time) ;

        return data ;
    }




    /**
     * Gets informations from Fragment3 and share them with other Fragments
     * @param stopTime, selected StopTime in Fragment3
     */
    public void saveInfoFromFragment_3(StopTime stopTime){
        this.selectedStopTime  = stopTime;

        Log.i("Info","Info from Fragment3  : stop_time = "+stopTime.getArrivalTime() );
    }


    public Map<String,String> provideInfoDataToFragment4(){

        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
        Date dt1= null;
        try { dt1 = format1.parse(selectedDate); } catch (ParseException e) { e.printStackTrace();}

        DateFormat format2=new SimpleDateFormat("EEEE", Locale.US);

        String finalDay = format2.format(dt1);

        Map<String,String> data = new HashMap<>();
        data.put("route_id",selectedBusRoute.getId()) ;
        data.put("stop_id",selectedStop.getId()) ;
        data.put("direction_id",""+directionId) ;
        data.put("day",""+finalDay) ;
        data.put("arrival_time",""+full_time) ;

        Log.i("Info","Info for Fragment3  : arrival_time = "+full_time );


        return data ;
    }

































    /**
     @param fragmentIndex
      * @return a found Fragment
     * @throws IllegalArgumentException, The value of parameter fragmentIndex must be between -1 and "+NB_FRAGMENTS+". "
     */
    public static Fragment getFragment(int fragmentIndex) throws IllegalArgumentException {
        if(fragmentIndex >= NB_FRAGMENTS || fragmentIndex < 0  ){ throw new IllegalArgumentException("The value of parameter fragmentIndex must be between -1 and "+NB_FRAGMENTS+". ") ; }
        return fragmentsList.get(fragmentIndex) ;
    }


    public  void selectFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction() ;
        fragmentTransaction.replace(R.id.frameLayout, fragment) ;
        fragmentTransaction.commit() ;

    }



    public void goToNextFragment(int fragmentIndex) throws IllegalArgumentException {
        if(fragmentIndex >= NB_FRAGMENTS || fragmentIndex < 0  ){ throw new IllegalArgumentException("The value of parameter fragmentIndex must be between -1 and "+NB_FRAGMENTS+". ") ; }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction() ;
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                .replace(R.id.frameLayout, MainActivity.getFragment(fragmentIndex)) ;
        fragmentTransaction.commit() ;
    }

    public void goToPreviousFragment(int fragmentIndex) throws IllegalArgumentException {
        if(fragmentIndex >= NB_FRAGMENTS || fragmentIndex < 0  ){ throw new IllegalArgumentException("The value of parameter fragmentIndex must be between -1 and "+NB_FRAGMENTS+". ") ; }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction() ;
        fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,R.anim.enter_right_to_left,R.anim.exit_right_to_left)
                .replace(R.id.frameLayout, MainActivity.getFragment(fragmentIndex)) ;
        fragmentTransaction.commit() ;
    }






}