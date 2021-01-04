package fr.istic.mob.star2eg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import fr.istic.mob.star2eg.fragments.Fragment1;
import fr.istic.mob.star2eg.fragments.Fragment2;
import fr.istic.mob.star2eg.fragments.Fragment3;
import fr.istic.mob.star2eg.modeles.StarContract;

public class MainActivity extends AppCompatActivity {

    private static List<Fragment> fragmentsList= new ArrayList<>() ;
    private int currentFragmentNumber = 0 ;
    private static int NB_FRAGMENTS = 3 ;

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