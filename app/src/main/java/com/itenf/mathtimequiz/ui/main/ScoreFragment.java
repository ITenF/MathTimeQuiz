package com.itenf.mathtimequiz.ui.main;

import androidx.activity.OnBackPressedCallback;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
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

import com.itenf.mathtimequiz.MainActivity;
import com.itenf.mathtimequiz.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.itenf.mathtimequiz.R.drawable.ic_addsignroundbckgrnd28dpx28dp;

public class ScoreFragment extends Fragment {

    private MainViewModel mViewModel;
    private Button scoreNumberBtn;
    private TextView numberRangeTxtView;
    private Button timeBtn;
    private TextView highScoreBtn;
    private Button startGameBtn;
    private Button typeArithmOpImageBtn;
    private Button homeButton;


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

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                //do nothing -- otherwise if you would be able to turn back the timer gets mixed up
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);




        //to make connection to Viewmodel
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        //
        scoreNumberBtn = view.findViewById(R.id.scoreNumberBtn);
        timeBtn = view.findViewById(R.id.timeBtn);
        numberRangeTxtView = view.findViewById(R.id.getallenVanBtn);
        startGameBtn = view.findViewById(R.id.startNewGameBtn);
        homeButton = view.findViewById(R.id.homeBtn);
        highScoreBtn = view.findViewById(R.id.highScoreBtn);
        typeArithmOpImageBtn = view.findViewById(R.id.typeArithmOpImageBtn);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_scoreFragment_to_chooseArithmeticOperationFragment);
            }
        });
        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_scoreFragment_to_playFieldFragment);
            }
        });

        //set the text of the score
        String scoreBtnText = mViewModel.getScore() + " " + view.getResources().getString(R.string.sommen);
        scoreNumberBtn.setText(scoreBtnText );

        //set the text on the button that shows the type of sums
        switch (mViewModel.getTypeArithmeticExpression()) {
            case "+":
                typeArithmOpImageBtn.setText( getResources().getString(R.string.plus));
                typeArithmOpImageBtn.setCompoundDrawablesWithIntrinsicBounds(
                null, null, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_addsignroundbckgrnd28dpx28dp , null), null);
                break;
            case "-":
                typeArithmOpImageBtn.setText( getResources().getString(R.string.min));
                typeArithmOpImageBtn.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, ResourcesCompat.getDrawable(getResources() , R.drawable.ic_minsignroundbckgrnd28dpx28dp , null) , null);
                break;
            case "*":
                typeArithmOpImageBtn.setText( this.getResources().getString(R.string.vermenigvuldig));
                typeArithmOpImageBtn.setCompoundDrawablesWithIntrinsicBounds(
                       // null, null, this.getResources().getDrawable(R.drawable.ic_multiplysignroundbckgrnd28x28dp), null);
                        null, null, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_multiplysignroundbckgrnd28x28dp , null), null);
                break;
            case "/":
                typeArithmOpImageBtn.setText( this.getResources().getString(R.string.delen));
                typeArithmOpImageBtn.setCompoundDrawablesWithIntrinsicBounds(
                        //null, null, this.getResources().getDrawable(R.drawable.ic_dividesignroundbckgrnd28x28dp), null);
                        null, null, ResourcesCompat.getDrawable(getResources() , R.drawable.ic_dividesignroundbckgrnd28x28dp , null), null);

                break;
            default:
                typeArithmOpImageBtn.setText( this.getResources().getString(R.string.nietGevonden));
        }

        //scoreKey is used as the key of the SharedPreferences to store the score as as key-value pair:
        String scoreKey = "HighScore_" + mViewModel.getTypeArithmeticExpression() + "_" + mViewModel.getNumberRange() + "_" + mViewModel.getNumberOfSeconds();
        checkAndStoreHighScore(scoreKey);

        //set the text of the range of numbers that are used to play with in the scoreFragment
        String numberRangeText = getResources().getString(R.string.streepjeEn1) + mViewModel.getNumberRange();
        numberRangeTxtView.setText(numberRangeText );
        //set the text of the Time of playing in the scoreFragment
        String timeBtnText = mViewModel.getNumberOfSeconds() + " " +  getResources().getString(R.string.sec);
        timeBtn.setText(timeBtnText);

        //reset All settings before a new game can start
       resetAll();

    }

    public void checkAndStoreHighScore(String scoreKey) {
        try {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            //to clear all values in sharedPreferences for debubbing:
            //editor.clear().commit();

            String score = String.valueOf(mViewModel.getScore());
            String textHighscore;
            // if there is NOT already a highscore for this game make a new highscore for this combination
            if (!sharedPref.contains(scoreKey)) {
                editor.putInt(scoreKey, mViewModel.getScore());
                editor.apply();
                textHighscore =  getResources().getString(R.string.hoogsteScore) + " " + score + " " + getResources().getString(R.string.sommen);
                highScoreBtn.setText( textHighscore);

                //there is already a highscore for this combination so look if the new score is higher then the saved one in shared Pref and set mew highscore
            } else if (sharedPref.getInt(scoreKey, 0) < mViewModel.getScore()) {
                editor.putInt(scoreKey, mViewModel.getScore());
                editor.apply();
                textHighscore = getResources().getString(R.string.nieuweHoogsteScore) + " " + score +  " " +  getResources().getString(R.string.sommen);
                highScoreBtn.setText( textHighscore );
                // Log.i("TagFloor" , "ScoreFragment regel 147 new score is: " + mViewModel.getScore());

            } else {//the new score is lower then the saved highscore so print the saved highscore in shared pref
                textHighscore = getResources().getString(R.string.hoogsteScoreTotNuToe) + " " + sharedPref.getInt(scoreKey, 0) +  " " + getResources().getString(R.string.sommen);
                highScoreBtn.setText(textHighscore );

            }

        }catch (Exception e){
            Log.i("Exception" , e + " SharedPreferences not available");
        }

    }

    public void resetAll(){
        //reset score
        mViewModel.setScore(0);
        //reset Timer
        mViewModel.setNumberOfSeconds(30);
        //reset NumberRange
        mViewModel.setNumberRange(10);
        //set startGame button listener
    }
}