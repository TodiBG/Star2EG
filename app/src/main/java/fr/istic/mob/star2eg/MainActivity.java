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

import java.util.ArrayList;
import java.util.List;

import fr.istic.mob.star2eg.fragments.Fragment1;
import fr.istic.mob.star2eg.fragments.Fragment2;
import fr.istic.mob.star2eg.fragments.Fragment3;
import fr.istic.mob.star2eg.modeles.BusRoute;
import fr.istic.mob.star2eg.modeles.StarContract;

public class MainActivity extends AppCompatActivity {

    private static List<Fragment> fragmentsList= new ArrayList<>() ;
    private static int NB_FRAGMENTS = 3 ;


    private String selectedDate ;
    private int time_hours = 0 ;
    private int time_minutes = 0 ;
    private BusRoute selectedBusRoute ;
    private List<String>  directionsList;
    private int directionId = 0 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentsList.add(new Fragment1(this));
        fragmentsList.add(new Fragment2(this));
        fragmentsList.add(new Fragment3(this));

        selectFragment(fragmentsList.get(0));


        Cursor cursor = getContentResolver().query(StarContract.BusRoutes.CONTENT_URI, null, null, null,null);

        int i = 0 ;
        while (cursor.moveToNext()){

            i++ ;
        }

        System.out.println(" ----------------- cursor moves "+i+" times ----------------- ");


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
     * @param time_hours, hour selected in Fragment1
     * @param time_minutes minute selected in Fragment1
     * @param selectedBusRoute , BusRoute selected in Fragment1
     * @param directionsList, List of directions for the BusRoute selected in Fragment1
     * @param directionId, Id of direction selected in Fragment1
     */
    public void saveInfoFromFragment_1(String selectedDate, int time_hours, int time_minutes, BusRoute selectedBusRoute, List<String> directionsList, int directionId){

        this.selectedDate  = selectedDate;
        this.time_hours = time_hours ;
        this.time_minutes = time_minutes ;
        this.selectedBusRoute = selectedBusRoute;
        this.directionsList = directionsList;
        this.directionId = directionId ;


        Log.i("Info","Info from Fragment1  : date = "+selectedDate+" , hour = "+time_hours+" minute = "+time_minutes+" , bus =  "+selectedBusRoute.getShortName()+", direction =  "+directionsList.get(directionId));

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