package com.itenf.mathtimequiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.itenf.mathtimequiz.ui.main.MainFragment;
import com.itenf.mathtimequiz.ui.main.MainViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        //To implement Viewmodel
        //final MainViewModel mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

    }



}