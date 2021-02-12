package com.itenf.mathtimequiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;

import com.itenf.mathtimequiz.ui.main.MainFragment;
import com.itenf.mathtimequiz.ui.main.MainViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }

        //To implement Viewmodel
        final MainViewModel mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

    }



}