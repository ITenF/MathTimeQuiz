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

import com.itenf.mathtimequiz.LifeCycleObserver;
import com.itenf.mathtimequiz.R;

public class ChooseArithmeticOperationFragment extends Fragment {

    private MainViewModel mViewModel;
    View view;

    public static ChooseArithmeticOperationFragment newInstance() {
        return new ChooseArithmeticOperationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.choose_arithmetic_operation  , container, false);
        return view;
    }



    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        //the onclick-listeners for the buttons
        view.findViewById(R.id.plusTxtImageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusBtnOnClick();
            }
        });
        view.findViewById(R.id.minTxtImageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minBtnOnClick();
            }
        });
        view.findViewById(R.id.multiplyTxtImageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiplyBtnOnClick();
            }
        });
        view.findViewById(R.id.divideTxtImageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                divisionBtnOnClick();
            }
        });


    }

    //go to next fragment: ChooseRangeNumbersFragment
    public void goToChooseRangeNumbersFragment(){
        Navigation.findNavController(view).navigate(R.id.action_chooseArithmeticOperationFragment_to_chooseRangeNumbersFragment);
    }

    public void plusBtnOnClick(){
        mViewModel.setTypeArithmeticExpression("+");
        mViewModel.setNumberRange(10);
        mViewModel.setNumberOfSeconds(30);
        goToChooseRangeNumbersFragment();
    }

    public void minBtnOnClick(){
        mViewModel.setTypeArithmeticExpression("-");
        mViewModel.setNumberRange(10);
        mViewModel.setNumberOfSeconds(30);
        goToChooseRangeNumbersFragment();
    }

    public void multiplyBtnOnClick(){
        mViewModel.setTypeArithmeticExpression("*");
        mViewModel.setNumberRange(10);
        mViewModel.setNumberOfSeconds(30);
        goToChooseRangeNumbersFragment();
    }

    public void divisionBtnOnClick(){
        mViewModel.setTypeArithmeticExpression("/");
        mViewModel.setNumberRange(10);
        mViewModel.setNumberOfSeconds(30);
        goToChooseRangeNumbersFragment();
    }



}