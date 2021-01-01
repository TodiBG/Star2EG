package fr.istic.mob.star2eg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import fr.istic.mob.star2eg.fragments.Fragment1;
import fr.istic.mob.star2eg.fragments.Fragment2;
import fr.istic.mob.star2eg.fragments.Fragment3;

public class MainActivity extends AppCompatActivity {

    private Fragment1  fragment1;
    private Fragment2 fragment2 ;
    private Fragment3 fragment3 ;
    private Spinner spinner  ;
    List<String> names ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.spinner) ;
        fragment1 = new Fragment1() ;
        fragment2 = new Fragment2() ;
        fragment3 = new Fragment3() ;

        names = new ArrayList<>() ;
        names.add("name 1 ") ;
        names.add("name 2 ") ;
        names.add("name 3 ") ;
        names.add("name 4 ") ;
        names.add("name 5 ") ;
        names.add("name 6 ") ;
        names.add("name 7 ") ;
        names.add("name 8 ") ;
        names.add("name 9 ") ;
        names.add("name 10 ") ;
        names.add("name 11 ") ;
        names.add("name 12 ") ;



        ArrayAdapter<String> arrayAdapte  = new ArrayAdapter<>(MainActivity.this, R.layout.item, names) ;
        arrayAdapte.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinner.setAdapter(arrayAdapte);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0 :
                        selectFragment(fragment1);
                        break;
                    case 1 :
                        selectFragment(fragment2);
                        break;
                    case 2 :
                        selectFragment(fragment3);
                    break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void selectFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction() ;
        fragmentTransaction.replace(R.id.frameLayout, fragment) ;
        fragmentTransaction.commit() ;

    }


}