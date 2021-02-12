package com.itenf.mathtimequiz.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itenf.mathtimequiz.R;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.main_fragment, container, false);
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(false);
    }



    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        // TODO: Use the ViewModel

        Handler h = new Handler();
        h.postDelayed(r, 3000); // <-- the "1000" is the delay time in miliseconds.

    }


    //this will be executed after the right answer is chosen, with a delay of 1 sec
    Runnable r = new Runnable() {
        @Override
        public void run(){
            ChooseArithmeticOperationFragment newFragment = new ChooseArithmeticOperationFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(MainFragment.this)
                    .replace(R.id.container , newFragment)
                    .addToBackStack(null)
                    .commit();
        }
    };

    //to hide the app-toolbar:
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }




}