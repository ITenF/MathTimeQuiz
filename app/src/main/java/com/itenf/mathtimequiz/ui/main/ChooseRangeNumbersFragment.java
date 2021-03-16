package com.itenf.mathtimequiz.ui.main;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.itenf.mathtimequiz.R;

public class ChooseRangeNumbersFragment extends Fragment {

    private MaterialButtonToggleGroup materialButtonToggleGroupRange;
    private MaterialButtonToggleGroup materialButtonToggleGroupSeconds;

    private com.itenf.mathtimequiz.ui.main.MainViewModel mViewModel;
    View view;

    public static ChooseRangeNumbersFragment newInstance() {
        return new ChooseRangeNumbersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.choose_range_numbers_fragment, container, false);
        return view;

    }



    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel  =  new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        //set the first button of the button togglegroup with range numbers as selected
        materialButtonToggleGroupRange = view.findViewById(R.id.rangeNumbers);
        materialButtonToggleGroupRange.check(R.id.text1_10_Btn);
        //set the first button of the button togglegroup with range seconds as selected
        materialButtonToggleGroupSeconds = view.findViewById(R.id.rangeSec);
        materialButtonToggleGroupSeconds.check(R.id.time30secBtn);

        //the onclick-listeners for the buttons
        view.findViewById(R.id.text1_10_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1_10_BtnOnClick();
            }
        });

        view.findViewById(R.id.text_1_20Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1_20_BtnOnClick();
            }
        });

        view.findViewById(R.id.text_1_30Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1_30_BtnOnClick();
            }
        });

        view.findViewById(R.id.text_1_40Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1_40_BtnOnClick();
            }
        });
        view.findViewById(R.id.text_1_50Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1_50_BtnOnClick();
            }
        });

        view.findViewById(R.id.text1_100_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1_100_btnOnClick();
            }
        });

        view.findViewById(R.id.time30secBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time30secBtnOnClick();
            }
        });

        view.findViewById(R.id.time45secBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time45secBtnOnClick();
            }
        });

        view.findViewById(R.id.time60secBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time60secBtnOnClick();
            }
        });

        view.findViewById(R.id.startGameBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGameBtnOnClick();
            }
        });

    }




        //Implementing all Button clicks:
    //For the number range
    public void text1_10_BtnOnClick(){
        mViewModel.setNumberRange(10);
        //Log.i("TagFloor" , "ChooseRangeNumbersFragment line 90 numberRange is:"  + mViewModel.getNumberRange());
        //Log.i("TagFloor" , "ChooseRangeNumbersFragment line 90 state button is:"  + getView().findViewById(R.id.text1_10_Btn).getMeasuredState());
        getView().findViewById(R.id.text1_10_Btn).setSelected(true);

    }
    public void text1_20_BtnOnClick(){
        mViewModel.setNumberRange(20);
        getView().findViewById(R.id.text_1_20Btn).setSelected(true);
    }
    public void text1_30_BtnOnClick(){ mViewModel.setNumberRange(30); }
    public void text1_40_BtnOnClick(){ mViewModel.setNumberRange(40); }
    public void text1_50_BtnOnClick(){ mViewModel.setNumberRange(50); }
    public void text1_100_btnOnClick(){
        mViewModel.setNumberRange(100);
    }

    //for the time range
    public void time30secBtnOnClick(){
        mViewModel.setNumberOfSeconds(30);
    }
    public void time45secBtnOnClick(){
        mViewModel.setNumberOfSeconds(45);
    }
    public void time60secBtnOnClick(){
        mViewModel.setNumberOfSeconds(60);
    }

    public void startGameBtnOnClick(){
        Navigation.findNavController(view).navigate(R.id.action_chooseRangeNumbersFragment_to_playFieldFragment);
    }


}