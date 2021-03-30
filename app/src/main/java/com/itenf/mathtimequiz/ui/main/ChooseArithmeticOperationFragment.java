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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //observe lifecycle
        getLifecycle().addObserver(new LifeCycleObserver());
        //setHasOptionsMenu(false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        view.findViewById(R.id.gameTxtImageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBtnOnClick();
            }
        });

    }


    public void goToChooseRangeNumbersFragment(){
        Navigation.findNavController(view).navigate(R.id.action_chooseArithmeticOperationFragment_to_chooseRangeNumbersFragment);
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

    public void gameBtnOnClick(){
        mViewModel.setTypeArithmeticExpression("#");
        Navigation.findNavController(view).navigate(R.id.action_chooseArithmeticOperationFragment_to_spel_Fragment);

    }


}