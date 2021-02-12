package com.itenf.mathtimequiz.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.itenf.mathtimequiz.R;

import java.util.ArrayList;
import java.util.List;

public class ScoreFragment extends Fragment {

    private MainViewModel mViewModel;
    private TextView scoreNumberTxtView;
    private TextView numberRangeTxtView;
    private TextView timeTxtView;
    private TextView highScoreTxtView;
    private Button startGameBtn;

    ArrayList<ArrayList<String>> highScoreList;


    public static ScoreFragment newInstance() {
        return new ScoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.score_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //to make connection to Viewmodel
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        //
        scoreNumberTxtView  = view.findViewById(R.id.scoreNumberTxtView);
        timeTxtView = view.findViewById(R.id.timeTxtView);
        numberRangeTxtView = view.findViewById(R.id.numberRangeTxtView);
        startGameBtn = view.findViewById(R.id.startNewGameBtn);
        highScoreTxtView = view.findViewById(R.id.highScoreTxtView);

        String scoreKey = "HighScore_" + mViewModel.getTypeArithmeticExpression() + "_" + String.valueOf(mViewModel.getNumberRange()) + "_" + String.valueOf(mViewModel.getNumberOfSeconds());
        Log.i("TagFloor" , "ScoreFragment regel 64: scoreKey is: " + scoreKey);

        checkAndStoreHighScore(scoreKey);
        //setText of the score and time
       // Log.i("TagFloor" , "ScoreFragment regel 55: score is: " + mViewModel.getScore());
        scoreNumberTxtView.setText(String.valueOf(mViewModel.getScore()) + "  " + mViewModel.getTypeArithmeticExpression() + " sums");
        //reset score
        mViewModel.setScore(0);
        //set range of numbers that are used to play with
        numberRangeTxtView.setText("with range of numbers: " + mViewModel.getNumberRange());
        //set Time of playing
        timeTxtView.setText(String.valueOf(mViewModel.getNumberOfSeconds())  + " sec");
        //reset Timer
        mViewModel.setNumberOfSeconds(30);


        //set startGame button listener
        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment newFragment = new MainFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(ScoreFragment.this)
                        .replace(R.id.container , newFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    public void checkAndStoreHighScore( String scoreKey){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        //check if there is already a highscore for this game with the right expr , range and score  __ if there is not the value will be 0
        if(sharedPref.getInt(scoreKey, 0) == 0){
            //make a new highscore for this combination
            editor.putInt(scoreKey , mViewModel.getScore());
            editor.apply();
            highScoreTxtView.setText("Highscore is "  + String.valueOf(mViewModel.getScore()));
            Log.i("TagFloor" , "ScoreFragment regel 103  REALLY NEW score is: " + mViewModel.getScore());
        }else {//there is already a highscore for this combination so look if the new score is higher then the older one
            if(sharedPref.getInt(scoreKey, 0) < mViewModel.getScore()){
                //set mew highscore
                editor.putInt(scoreKey , mViewModel.getScore());
                editor.apply();
                highScoreTxtView.setText("NEW Highscore is " + String.valueOf(mViewModel.getScore()));
                Log.i("TagFloor" , "ScoreFragment regel 107 new score is: " + mViewModel.getScore());
            }
        }




    }

}