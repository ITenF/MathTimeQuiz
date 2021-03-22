package com.itenf.mathtimequiz.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.itenf.mathtimequiz.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ScoreFragment extends Fragment {

    private MainViewModel mViewModel;
    private Button scoreNumberBtn;
    private TextView numberRangeTxtView;
    private Button timeBtn;
    private TextView highScoreBtn;
    private Button startGameBtn;
    private Button typeArithmOpImageBtn;


    public static ScoreFragment newInstance() {
        return new ScoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.score_fragment, container, false);
    }




    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //to make connection to Viewmodel
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        //
        scoreNumberBtn = view.findViewById(R.id.scoreNumberBtn);
        timeBtn = view.findViewById(R.id.timeBtn);
        numberRangeTxtView = view.findViewById(R.id.getallenVanBtn);
        startGameBtn = view.findViewById(R.id.startNewGameBtn);
        highScoreBtn = view.findViewById(R.id.highScoreBtn);
        typeArithmOpImageBtn = view.findViewById(R.id.typeArithmOpImageBtn);

        //set the text of the score
        String scoreBtnText = mViewModel.getScore() + " " + view.getResources().getString(R.string.sommen);
        scoreNumberBtn.setText(scoreBtnText );

        //set the text on the button that shows the type of sums
        switch (mViewModel.getTypeArithmeticExpression()) {
            case "+":
                typeArithmOpImageBtn.setText( getResources().getString(R.string.plus));
                typeArithmOpImageBtn.setCompoundDrawablesWithIntrinsicBounds(
                        null, null, this.getResources().getDrawable(R.drawable.ic_addsignroundbckgrnd28dpx28dp), null);
                break;
            case "-":
                typeArithmOpImageBtn.setText( getResources().getString(R.string.min));
                typeArithmOpImageBtn.setCompoundDrawablesWithIntrinsicBounds(
                        null, null, this.getResources().getDrawable(R.drawable.ic_minsignroundbckgrnd28dpx28dp), null);
                break;
            case "*":
                typeArithmOpImageBtn.setText( this.getResources().getString(R.string.vermenigvuldig));
                typeArithmOpImageBtn.setCompoundDrawablesWithIntrinsicBounds(
                        null, null, this.getResources().getDrawable(R.drawable.ic_multiplysignroundbckgrnd28x28dp), null);
                break;
            case "/":
                typeArithmOpImageBtn.setText( this.getResources().getString(R.string.delen));
                typeArithmOpImageBtn.setCompoundDrawablesWithIntrinsicBounds(
                        null, null, this.getResources().getDrawable(R.drawable.ic_dividesignroundbckgrnd28x28dp), null);
                break;
            default:
                typeArithmOpImageBtn.setText( this.getResources().getString(R.string.nietGevonden));
        }

        //scoreKey is used as the key of the SharedPreferences to store the score as as key-value pair:
        String scoreKey = "HighScore_" + mViewModel.getTypeArithmeticExpression() + "_" + mViewModel.getNumberRange() + "_" + mViewModel.getNumberOfSeconds();
        //Log.i("TagFloor", "ScoreFragment regel 64: scoreKey is: " + scoreKey);

        checkAndStoreHighScore(scoreKey);
        // Log.i("TagFloor" , "ScoreFragment regel 55: score is: " + mViewModel.getScore());


        //reset score
        mViewModel.setScore(0);
        //set the text of the range of numbers that are used to play with in the scoreFragment
        String numberRangeText = getResources().getString(R.string.streepjeEn1) + mViewModel.getNumberRange();
        numberRangeTxtView.setText(numberRangeText );
        //set the text of the Time of playing in the scoreFragment
        String timeBtnText = mViewModel.getNumberOfSeconds() + " " +  getResources().getString(R.string.sec);
        timeBtn.setText(timeBtnText);

        //reset Timer
        mViewModel.setNumberOfSeconds(30);
        //set startGame button listener
        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_scoreFragment_to_chooseArithmeticOperationFragment);
            }
        });

    }

    public void checkAndStoreHighScore(String scoreKey) {

        //Log.i("TagFloor" , "ScoreFragment regel 129  method checkAndStoreHighScore is started");
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //to clear all values in sharedPreferences for debubbing:
        //editor.clear().commit();


        //Log.i("TagFloor" , "ScoreFragment regel 131  method checkAndStoreHighScore is started");
        String score = String.valueOf(mViewModel.getScore());
        String textHighscore;
        if (!sharedPref.contains(scoreKey)) {// if there is not already a highscore for this game make a new highscore for this combination
            editor.putInt(scoreKey, mViewModel.getScore());
            editor.apply();
            textHighscore =  getResources().getString(R.string.hoogsteScore) + " " + score + " " + getResources().getString(R.string.sommen);
            highScoreBtn.setText( textHighscore);
            //Log.i("TagFloor" , "ScoreFragment regel 135  REALLY NEW score is: " + mViewModel.getScore());

        } else if (sharedPref.getInt(scoreKey, 0) < mViewModel.getScore()) {//there is already a highscore for this combination so look if the new score is higher then the saved one in shared Pref
            //set mew highscore
            editor.putInt(scoreKey, mViewModel.getScore());
            editor.apply();
            textHighscore = getResources().getString(R.string.nieuweHoogsteScore) + " " + score +  " " +  getResources().getString(R.string.sommen);
            highScoreBtn.setText( textHighscore );
            // Log.i("TagFloor" , "ScoreFragment regel 147 new score is: " + mViewModel.getScore());


        } else {//the new score is lower then the saved highscore so print the saved highscore in shared pref
            //Log.i("TagFloor" , "ScoreFragment regel 149 do nothing because the highscore is lower");}
            textHighscore = getResources().getString(R.string.hoogsteScoreTotNuToe) + " " + sharedPref.getInt(scoreKey, 0) +  " " + getResources().getString(R.string.sommen);
            highScoreBtn.setText(textHighscore );

        }

    }
}