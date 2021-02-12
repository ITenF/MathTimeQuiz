package com.itenf.mathtimequiz.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itenf.mathtimequiz.R;

public class ChooseArithmeticOperationFragment extends Fragment {

    private MainViewModel mViewModel;

    public static ChooseArithmeticOperationFragment newInstance() {
        return new ChooseArithmeticOperationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choose_arithmetic_operation  , container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //the onclick-listeners for the buttons
        view.findViewById(R.id.plusBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusBtnOnClick();
            }
        });
        view.findViewById(R.id.minBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minBtnOnClick();
            }
        });
        view.findViewById(R.id.multiplyBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiplyBtnOnClick();
            }
        });
        view.findViewById(R.id.divisionBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                divisionBtnOnClick();
            }
        });

    }


    public void goToChooseRangeNumbersFragment(){
        ChooseRangeNumbersFragment newFragment = new ChooseRangeNumbersFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .remove(ChooseArithmeticOperationFragment.this)
                .replace(R.id.container , newFragment)
                .addToBackStack(null)
                .commit();
    }

    public void plusBtnOnClick(){
        mViewModel.setTypeArithmeticExpression("+");
        goToChooseRangeNumbersFragment();
    }

    public void minBtnOnClick(){
        mViewModel.setTypeArithmeticExpression("-");
        goToChooseRangeNumbersFragment();
    }

    public void multiplyBtnOnClick(){
        mViewModel.setTypeArithmeticExpression("*");
        goToChooseRangeNumbersFragment();
    }

    public void divisionBtnOnClick(){
        mViewModel.setTypeArithmeticExpression("/");
        goToChooseRangeNumbersFragment();
    }

}