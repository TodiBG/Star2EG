package fr.istic.mob.star2eg.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import fr.istic.mob.star2eg.MainActivity;
import fr.istic.mob.star2eg.R;

public class Fragment3 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        getView().findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction() ;
                fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,R.anim.enter_right_to_left,R.anim.exit_right_to_left)
                        .replace(R.id.frameLayout, MainActivity.getFragment(1)) ;
                fragmentTransaction.commit() ;
            }
        });


        getView().findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction() ;
                fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.frameLayout, MainActivity.getFragment(0)) ;
                fragmentTransaction.commit() ;
            }
        });

    }
}