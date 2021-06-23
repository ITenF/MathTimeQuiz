package com.itenf.mathtimequiz.ui.main;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.itenf.mathtimequiz.R;
import java.util.ArrayList;
import java.util.Collections;


public class PlayFieldFragment extends Fragment {

    private MainViewModel mViewModel;
    private TextView nrOfSecTxtView;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;//to set the number of seconds of recording and playing
    private TextView firstNumberTxtView;
    private TextView secondNumberTxtView;
    private TextView arithmeticExpTxtView;
    private TextView scoreTxtView;
    private Button answer1Button;
    private Button answer2Button;
    private Button answer3Button;
    private Button answer4Button;
    View view;
    private int playScore;



    public static PlayFieldFragment newInstance() {
        return new PlayFieldFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.play_field_fragment, container, false);
        return view;
    }



    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //to make connection to Viewmodel
        mViewModel =  new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        //find all views
        nrOfSecTxtView = view.findViewById(R.id.nrOfSecTxtView);
        progressBar = view.findViewById(R.id.timeProgressBar);
        firstNumberTxtView = view.findViewById(R.id.EditTextNumber1);
        secondNumberTxtView = view.findViewById(R.id.editTextNumber2);
        arithmeticExpTxtView = view.findViewById(R.id.arithmeticExprTxtbox);
        scoreTxtView = view.findViewById(R.id.scoreTxtView);
        answer1Button = view.findViewById(R.id.answer1Btn);
        answer2Button = view.findViewById(R.id.answer2Btn);
        answer3Button = view.findViewById(R.id.answer3Btn);
        answer4Button = view.findViewById(R.id.answer4Btn);

        //set answerButton listeners:
        answer1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                checkAnswer(answer1Button);
            }
        });
        answer2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                checkAnswer(answer2Button);
            }
        });
        answer3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                checkAnswer(answer3Button);
            }
        });
        answer4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                checkAnswer(answer4Button);
            }
        });

        //make first sum
        mViewModel.setScore(0);
        playScore = 0;
        int correctAnswer = makeSumAndSetSumInPlayField(mViewModel.getTypeArithmeticExpression());
        mViewModel.setCorrectAnswer(correctAnswer);
        setAnswerButtons();
        setTheTimerAndProgressbar(mViewModel.getNumberOfSeconds());
    }

    //create a random number between the range that is chosen 1-10 or 1-200
    public int getRandomNumber( int maxValue){
        int min = 1;
       return  (int)(Math.random() * (maxValue - min + 1) + min);
    }


    //make a new sum from the chosen artithmetic expression,and Do set it in the textfields of the playfield and remember
    public int makeSumAndSetSumInPlayField(String typeOfArithmeticExpr){
        //make the first number of the sum
        int firstNumber = getRandomNumber(mViewModel.getNumberRange());
        firstNumberTxtView.setText(String.valueOf(firstNumber));
        //set type of sum
        arithmeticExpTxtView.setText(typeOfArithmeticExpr);
        //make the second number of the sum
        int secondNumber = getRandomNumber(mViewModel.getNumberRange());
        //look what the correct answer of the sum is
        int answerInt=0;
        if (typeOfArithmeticExpr.equals("+")){
            answerInt = firstNumber + secondNumber;
        }
        if (typeOfArithmeticExpr.equals("-")){
            // to make sure there are no negative numbers in answers
            if (secondNumber >= firstNumber){
                secondNumber = getRandomNumber(firstNumber);
            }
            answerInt = firstNumber - secondNumber;
        }
        if (typeOfArithmeticExpr.equals("*")){
            answerInt = firstNumber * secondNumber;
        }
        secondNumberTxtView.setText(String.valueOf(secondNumber));
        // !!!you make the division   (firstnumber*secondnumber)  / firstnumber = secondnumber  In this way you make sure there is a integer as an answerInt
        if (typeOfArithmeticExpr.equals("/")){
            int newFirstNumber = firstNumber*secondNumber;
            firstNumberTxtView.setText(String.valueOf(newFirstNumber));
            secondNumberTxtView.setText(String.valueOf(firstNumber));
            answerInt = secondNumber;
        }
        return answerInt;
    }


    //make a new sum from the chosen artithmetic expression, but don't set it in the textfields of the playfield, this is used to make the false answer buttons
    public int makeSum(String typeOfArithmeticExpr){
        //make the first number of the sum
        int firstNumber = getRandomNumber(mViewModel.getNumberRange());
        //make the second number of the sum
        int secondNumber = getRandomNumber(mViewModel.getNumberRange());
        //look what the correct answer of the sum is
        int answerInt=0;
        if (typeOfArithmeticExpr.equals("+")){
            answerInt = firstNumber + secondNumber;
        }
        if (typeOfArithmeticExpr.equals("-")){
            //answerInt = firstNumber - secondNumber;
            answerInt = getRandomNumber(mViewModel.getNumberRange());

        }
        if (typeOfArithmeticExpr.equals("*")){
            answerInt = firstNumber * secondNumber;
        }
        if (typeOfArithmeticExpr.equals("/")){
            // you make the division   (firstnumber*secondnumber)  / firstnumber = secondnumber  In this way you make sure there is a integer as an answerInt
            int newFirstNumber = firstNumber*secondNumber;
            //firstNumberTxtView.setText(String.valueOf(newFirstNumber));
            answerInt = newFirstNumber/firstNumber;
        }
        return answerInt;
    }


    //set right anwser in one of the 4 buttons en make the text of the other buttons randomly different from the right answer for numbers 1-10 they all have to be number
    public void setAnswerButtons(){
        //make an arrayList of 3 answers that are different then the corrrect answer en different from each other
        ArrayList<Integer> newAnswerArrayList = new ArrayList<>();
        for(int i=0 ; i<3 ; i++) {
            int newAnswer = makeSum(mViewModel.getTypeArithmeticExpression());
            //check if newAnswer is not the same as the correct answer or already is made as an anser or is smaller then the correctanswer-10   or is bigger then the correct answer +10
            while (newAnswer == mViewModel.getCorrectAnswer() || newAnswerArrayList.contains(newAnswer) || newAnswer < mViewModel.getCorrectAnswer()-10 || newAnswer > mViewModel.getCorrectAnswer()+10) {
                newAnswer = makeSum(mViewModel.getTypeArithmeticExpression());
            }
            newAnswerArrayList.add(newAnswer);
        }

        //make an array of the 4 answerbuttons
        ArrayList<Button> buttonArrayList = new ArrayList<>();
        buttonArrayList.add(answer1Button);
        buttonArrayList.add(answer2Button);
        buttonArrayList.add(answer3Button);
        buttonArrayList.add(answer4Button);
        //make a ArrayList of 4 integers with values 1,2,3,4
        ArrayList <Integer> integerList1of4 = new ArrayList<>(4);
        //fill the ArrayList met numbers 1 t/m 4
        for (int i=0 ; i<4 ; i++){
            integerList1of4.add(i);
        }
        //Shuffle the numbers in the arrayList:
        Collections.shuffle(integerList1of4);
        //set for each of the answerButton the answerText. The button that is the first number in the array gets the right answer
        int nrInArray = integerList1of4.get(0);
            buttonArrayList.get(nrInArray).setText(String.valueOf(mViewModel.getCorrectAnswer()));
        //for the other buttons
        //make new sums and answers and make sure there are three different answers and not the same as the correctAnswer of the real sum
        for (int i=1 ; i<4 ; i++){
            nrInArray = integerList1of4.get(i);
            buttonArrayList.get(nrInArray).setText(String.valueOf(newAnswerArrayList.get(i-1)));
        }
    }


//Checks if the answer on the button is the correct answer that is stored in the mViewmodel
    public void checkAnswer( Button buttonWithNummber){
        //get the number of the button
        int buttonNumber = Integer.parseInt(String.valueOf(buttonWithNummber.getText()));
        //the answer is not correct so turn the background color of the button red
        if (buttonNumber == mViewModel.getCorrectAnswer()){
            //raise the score with 1
           // mViewModel.setScore(mViewModel.getScore()+1);
            playScore++;
            scoreTxtView.setText(String.valueOf(playScore));
            //set the background of the right button to green
            try {
                buttonWithNummber.setBackgroundColor(getActivity().getResources().getColor(R.color.greenForRightAnswer));
            }catch (Exception e){
                Log.i("Exception" , e.toString() + " color green is not available");
            }
            //wait for 1 second to see the green color to show that the answer was right
            Handler h = new Handler();
            h.postDelayed(r, 250); // <-- the "250" is the delay time in miliseconds to be able to see the color change before the new sum appears.
        }else{
            try {
                buttonWithNummber.setBackgroundColor(getActivity().getResources().getColor(R.color.redForWrongAnswer));
            }catch (Exception e){
                Log.i("Exception" , e.toString() + " color is not available");
            }
            answer1Button.setClickable(false);
            answer2Button.setClickable(false);
            answer3Button.setClickable(false);
            answer4Button.setClickable(false);
            Handler wait = new Handler();
            //set wait punishment for wrong answer by disabling buttons
            wait.postDelayed(rWait, 1500); // <-- the "1500" is the delay time in miliseconds.
        }

    }


    //this will be executed after the right answer is chosen, with a delay of 1 sec
    Runnable r = new Runnable() {
        @Override
        public void run(){
            //remove the backgroundcolor of all the buttons
           //answer1Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
            removeBackgrdcolorOfAnswerBtns();
            //make new sum in playfield and store new correctanswer
            int newCorrectAnswer = makeSumAndSetSumInPlayField(mViewModel.getTypeArithmeticExpression());
            mViewModel.setCorrectAnswer(newCorrectAnswer);
            setAnswerButtons();
        }
    };


    //Stop the execution of the program for punishment for a wrong answer: after a while the buttons will be clickable again
    Runnable rWait = new Runnable() {
        @Override
        public void run(){
            answer1Button.setClickable(true);
            answer2Button.setClickable(true);
            answer3Button.setClickable(true);
            answer4Button.setClickable(true);
        }
    };


    public void setTheTimerAndProgressbar(int numSec ){

        final long numberOfMiliSeconds = numSec *1000;
        //Log.i("TagFloor"  , "PlayFieldFragment line 223 numberOfMiliseconds is:"  + numberOfMiliSeconds);
        final int maxTime = (int)numberOfMiliSeconds;
        progressBar.setMax(maxTime);
        countDownTimer = new CountDownTimer(numberOfMiliSeconds, 1000) {

            long maxWaarde = numberOfMiliSeconds;
            @Override
            public void onTick(long numberOfMiliSeconds) {

                try{
                    nrOfSecTxtView.setText(
                            (String.valueOf(numberOfMiliSeconds/1000) +  " "  + getActivity().getResources().getString(R.string.sec)));
                }catch (Exception e){
                    Log.i("LOG_TAG" ,  "String sec is niet gevonden "  + e );
                }

                progressBar.setProgress((int) (maxWaarde - numberOfMiliSeconds));
            }

            @Override
            public void onFinish() {
                    finishGame();

            }

        }.start();
    }




    public void finishGame(){
        //if the PlayFieldFragment is not active anymore then the score_fragment should not be constructed
        if (this.isVisible()) {
            //go to Score Fragment
            Navigation.findNavController(view).navigate(R.id.action_playFieldFragment_to_scoreFragment);
            mViewModel.setScore(playScore);
        }
    }

    //BUGFIX when the app is resumed after being in the background sometimes the timer and progressbar don't start up again. To prevent this from happening now everytime the
    // app is paused the user has to start the game again from the beginning
    @Override
    public void onResume(){
        super.onResume();
        //Log.i("LOG_TAG" , "onResume");
        playScore = 0;
        scoreTxtView.setText("0");
        progressBar.setProgress(0);
        countDownTimer.cancel();
        removeBackgrdcolorOfAnswerBtns();
        setTheTimerAndProgressbar(mViewModel.getNumberOfSeconds());
    }


    public void removeBackgrdcolorOfAnswerBtns(){
        try {
            answer1Button.setBackgroundColor(getActivity().getResources().getColor(R.color.primaryColor));
        }catch (Exception e){
            Log.i("Exception" , e.toString() + " color primary is not available");
        }
        //answer2Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
        try {
            answer2Button.setBackgroundColor(getActivity().getResources().getColor(R.color.primaryColor));
        }catch (Exception e){
            Log.i("Exception" , e.toString() + " color primary is not available");
        }
        //answer3Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
        try {
            answer3Button.setBackgroundColor(getActivity().getResources().getColor(R.color.primaryColor));
        }catch (Exception e){
            Log.i("Exception" , e.toString() + " color primary is not available");
        }
        //answer4Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
        try {
            answer4Button.setBackgroundColor(getActivity().getResources().getColor(R.color.primaryColor));
        }catch (Exception e){
            Log.i("Exception" , e.toString() + " color primary is not available");
        }

    }




}