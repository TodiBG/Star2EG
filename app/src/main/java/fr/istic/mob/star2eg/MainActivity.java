package fr.istic.mob.star2eg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

public class MainActivity extends AppCompatActivity {

    private static List<Fragment> fragmentsList= new ArrayList<>() ;
    private int currentFragmentNumber = 0 ;
    private static int NB_FRAGMENTS = 3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentsList.add(new Fragment1());
        fragmentsList.add(new Fragment2());
        fragmentsList.add(new Fragment3());

        selectFragment(fragmentsList.get(0));

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


}