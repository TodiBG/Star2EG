package fr.istic.mob.star2eg.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.istic.mob.star2eg.MainActivity;
import fr.istic.mob.star2eg.R;

public class Fragment2 extends Fragment {
    private MainActivity activity ;
    private ListView listView ;


    public Fragment2(MainActivity activity){
        this.activity  = activity ;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        getView().findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { activity.goToPreviousFragment(0); }
        });

        listView = (ListView)getView().findViewById(R.id.listView) ;

        String[][] repertoire = new String[][]{
                {"Donzelot", "arrêt 1"},
                {"Pont de Strasbourg", "arrêt 2"},
                {"Pont de Chateaudin", "arrêt 3"},
                {"Paul Bert", "arrêt 4"},
                {"Musée de beaux arts", "arrêt 5"},
                {"République", "arrêt 6"},
                {"Les Longs Champs", "arrêt 7"},
                {"Painlévé", "arrêt 8"},
                {"Robidou", "arrêt 9"},
        };

        List<HashMap<String, String>> liste = new ArrayList<>();

        HashMap<String, String> element;
        for (int i = 0; i < repertoire.length; i++) {
            element = new HashMap<String, String>();
            element.put("nom", repertoire[i][0]);
            element.put("tel", repertoire[i][1]);
            liste.add(element);
        }
        ListAdapter adapter = new SimpleAdapter(getContext(), liste, android.R.layout.simple_list_item_2, new String[]{"nom", "tel"}, new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity.goToNextFragment(2) ;
            }
        });

    }

}