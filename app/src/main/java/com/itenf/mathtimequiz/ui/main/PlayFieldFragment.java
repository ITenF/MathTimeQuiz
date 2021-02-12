package com.itenf.mathtimequiz.ui.main;

import androidx.core.util.TimeUtils;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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


    DecimalFormat df2 = new DecimalFormat("##0.00");

    public static PlayFieldFragment newInstance() {
        return new PlayFieldFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.play_field_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
                if(mViewModel.getTypeArithmeticExpression().equals("/")){
                    checkAnswerDivision(answer1Button);
                }else{
                    checkAnswer(answer1Button);
                }

            }
        });
        answer2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                if(mViewModel.getTypeArithmeticExpression().equals("/")){
                    checkAnswerDivision(answer2Button);
                }else{
                    checkAnswer(answer2Button);
                }
            }
        });
        answer3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                if(mViewModel.getTypeArithmeticExpression().equals("/")){
                    checkAnswerDivision(answer3Button);
                }else{
                    checkAnswer(answer3Button);
                }
            }
        });
        answer4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                if(mViewModel.getTypeArithmeticExpression().equals("/")){
                    checkAnswerDivision(answer4Button);
                }else{
                    checkAnswer(answer4Button);
                }
            }
        });

        //make first sum
        mViewModel.setScore(0);
        String typeOfArithExpr = mViewModel.getTypeArithmeticExpression();
        if(typeOfArithExpr.equals("+") || typeOfArithExpr.equals("-") || typeOfArithExpr.equals("*") ) {
            int correctAnswer = makeSumAndSetSumInPlayField(mViewModel.getTypeArithmeticExpression());
            mViewModel.setCorrectAnswer(correctAnswer);
            //Log.i("TagFloor" , "PlayFieldFragment.java line 73 correctAnswer is" + correctAnswer);
            //set Answer buttons
            setAnswerButtons();
        }else{
            Double correctAnsDbl = makeSumAndSetSumInPlayFieldForDivision();
            mViewModel.setCorrectAnswerDivDbl(correctAnsDbl);
            setAnswerButtonsForDivision();
        }
        setTheTimerAndProgressbar(mViewModel.getNumberOfSeconds());

    }

    //create a random number between the range that is chosen 1-10 or 1-200
    public int getRandomNumber(){
       int max = mViewModel.getNumberRange();
        //Log.i("TagFloor" , "PlayFieldFragment regel 49: max is: " + max);
        int min = 1;
        int randNumber = (int)(Math.random() * (max - min + 1) + min);
        //Log.i("TagFloor" , "PlayFieldFragment regel 85: randNumber is: " + randNumber);
       return randNumber;
    }


    //make a new sum from the chosen artithmetic expression,and Do set it in the textfields of the playfield and remember
    public int makeSumAndSetSumInPlayField(String typeOfArithmeticExpr){
        //make the first number of the sum
        int firstNumber = getRandomNumber();
        firstNumberTxtView.setText(String.valueOf(firstNumber));
        //look if the sum must be + - * or all
        arithmeticExpTxtView.setText(typeOfArithmeticExpr);
        //make the second number of the sum
        int secondNumber = getRandomNumber();
        secondNumberTxtView.setText(String.valueOf(secondNumber));
        //look what the correct answer of the sum is
        int answerInt=0;
        if (typeOfArithmeticExpr.equals("+")){
            answerInt = firstNumber + secondNumber;
        }
        if (typeOfArithmeticExpr.equals("-")){
            answerInt = firstNumber - secondNumber;
        }
        if (typeOfArithmeticExpr.equals("*")){
            answerInt = firstNumber * secondNumber;
        }
        return answerInt;
    }


    //make a new sum from the chosen artithmetic expression, but don't set it in the textfields of the playfield, this is used to make the false answer buttons
    public int makeSum(String typeOfArithmeticExpr){
        //make the first number of the sum
        int firstNumber = getRandomNumber();
        //make the second number of the sum
        int secondNumber = getRandomNumber();
        //look what the correct answer of the sum is
        int answerInt=0;
        if (typeOfArithmeticExpr.equals("+")){
            answerInt = firstNumber + secondNumber;
        }
        if (typeOfArithmeticExpr.equals("-")){
            answerInt = firstNumber - secondNumber;
        }
        if (typeOfArithmeticExpr.equals("*")){
            answerInt = firstNumber * secondNumber;
        }
        return answerInt;
    }

    //make a new sum from the chosen artithmetic expression,and Do set it in the textfields of the playfield and remember
    public Double makeSumAndSetSumInPlayFieldForDivision(){
        //make the first number of the sum
        int firstNumber = getRandomNumber();
        firstNumberTxtView.setText(String.valueOf(firstNumber));
        //look if the sum must be + - * or all
        arithmeticExpTxtView.setText(mViewModel.getTypeArithmeticExpression());
        //make the second number of the sum
        int secondNumber = getRandomNumber();
        secondNumberTxtView.setText(String.valueOf(secondNumber));
        //look what the correct answer of the sum is
        double answerDbl = (double)firstNumber / (double)secondNumber;
        //Log.i("TagFloor" , "PlayFieldFragment regel 182: real Double without format is " + answerDbl);
        return answerDbl;

    }

    //make a new division sum, but don't set it in the textfields of the playfield, this is used to make the false answer buttons
    public Double makeDvisionSum(){
        //make the first number of the sum
        int firstNumber = getRandomNumber();
        //Log.i("TagFloor" , "PlayFieldFragment regel 223: firstNumber of other sums is: "  + firstNumber);
        //make the second number of the sum
        int secondNumber = getRandomNumber();
        //Log.i("TagFloor" , "PlayFieldFragment regel 226: secondnumber of other sums is: "  + secondNumber);
        //look what the correct answer of the sum is
        Double answerDbl;
        answerDbl = ((double)firstNumber / (double)secondNumber);
        //Log.i("TagFloor" , "PlayFieldFragment regel 225: var answerDbl of all the other answers are : "  + answerDbl);
        return answerDbl;
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
        //Log.i("TagFloor" , "PlayFieldFragment regel 141: correct answer is: " + mViewModel.getCorrectAnswer());

    }

    //set right anwser in one of the 4 buttons en make the text of the other buttons randomly different from the right answer for numbers 1-10 they all have to be number
    public void setAnswerButtonsForDivision(){
        //make an arrayList of 3 answers that are different then the corrrect answer en different from each other
        ArrayList<String> newAnswerArrayList = new ArrayList<>();
        for(int i=0 ; i<3 ; i++) {
            double newAnswer =  makeDvisionSum();
           // Log.i("TagFloor" , "PlayFieldFragment regel 285: answer is: " + mViewModel.getCorrectAnswerDivDbl());

            double correctAnswerDbl = mViewModel.getCorrectAnswerDivDbl();
            //check if newAnswer is not the same as the correct answer or already is made as an anser or is smaller then the correctanswer-10   or is bigger then the correct answer +10
            while (newAnswer == correctAnswerDbl || newAnswerArrayList.contains(newAnswer) || newAnswer < correctAnswerDbl-10 || newAnswer > correctAnswerDbl+10)
            {
                newAnswer = makeDvisionSum();
            }
            newAnswerArrayList.add(df2.format(newAnswer));
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
        //set correct answer text with 2 decimals on one of the buttons
        //String correctAnswerDbl = mViewModel.getCorrectAnswerDbl();
        //String correctAsnwerStr = String.format("%.2f", correctAnswerDbl);
       // String correctAsnwerStr2 = df2.format(correctAnswerDbl);

        buttonArrayList.get(nrInArray).setText(df2.format(mViewModel.getCorrectAnswerDivDbl()));
        //for the other buttons
        //make new sums and answers and make sure there are three different answers and not the same as the correctAnswer of the real sum
        for (int i=1 ; i<4 ; i++){
            nrInArray = integerList1of4.get(i);
            //get the next answer in the false answer array:
           String otherAnswerStr = newAnswerArrayList.get(i-1);
           // String otherAnswerStr = String.format("%.2f" , otherAnswerDbl);
            //String otherAsnwerStr2 = df2.format(otherAnswerDbl);
            buttonArrayList.get(nrInArray).setText(otherAnswerStr);
        }
        //Log.i("TagFloor" , "PlayFieldFragment regel 141: correct answer is: " + mViewModel.getCorrectAnswer());
    }

    public void checkAnswer( Button buttonWithNummber){
        //get the number of the button
        int buttonNumber = Integer.parseInt(String.valueOf(buttonWithNummber.getText()));
        //the answer is not correct so turn the background color of the button red
        if (buttonNumber == mViewModel.getCorrectAnswer()){
            //raise the score with 1
            mViewModel.setScore(mViewModel.getScore()+1);
            scoreTxtView.setText(String.valueOf(mViewModel.getScore()));
            //set the background of the right button to green
            buttonWithNummber.setBackgroundResource(R.color.greenForRightAnswer);
            //wait for 1 second to see the green color to show that the answer was right
            Handler h = new Handler();
            h.postDelayed(r, 1000); // <-- the "1000" is the delay time in miliseconds.
        }else{
            buttonWithNummber.setBackgroundResource(R.color.redForWrongAnswer);
        }

    }

    public void checkAnswerDivision( Button buttonWithNummber){
        //get the number of the button
        String buttonAnswerStr = String.valueOf(buttonWithNummber.getText());
        //Log.i("TagFloor" , "PlayFieldFragment regel 337: buttonAnswerStr = " + buttonAnswerStr);
        //Log.i("TagFloor" , "PlayFieldFragment regel 351: real answer with format is = " + df2.format(mViewModel.getCorrectAnswerDivDbl()));
        //Double buttonNumber = Double.parseDouble(buttonAnswerStr);
        //the answer is not correct so turn the background color of the button red
        if (buttonAnswerStr.equals(df2.format(mViewModel.getCorrectAnswerDivDbl()))){
            //raise the score with 1
            mViewModel.setScore(mViewModel.getScore()+1);
            scoreTxtView.setText(String.valueOf(mViewModel.getScore()));
            //set the background of the right button to green
            buttonWithNummber.setBackgroundResource(R.color.greenForRightAnswer);
            //wait for 1 second to see the green color to show that the answer was right
            Handler h = new Handler();
            h.postDelayed(rDiv, 1000); // <-- the "1000" is the delay time in miliseconds.
        }else{
            buttonWithNummber.setBackgroundResource(R.color.redForWrongAnswer);
        }

    }

    //this will be executed after the right answer is chosen, with a delay of 1 sec
    Runnable r = new Runnable() {
        @Override
        public void run(){
            //remove the backgroundcolor of all the buttons
           answer1Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
            answer2Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
            answer3Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
            answer4Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
            //make new sum in playfield and store new correctanswer
            int newCorrectAnswer = makeSumAndSetSumInPlayField(mViewModel.getTypeArithmeticExpression());
            mViewModel.setCorrectAnswer(newCorrectAnswer);
            setAnswerButtons();
        }
    };

    //this will be executed after the right answer is chosen, with a delay of 1 sec
    Runnable rDiv = new Runnable() {
        @Override
        public void run(){
            //remove the backgroundcolor of all the buttons
            answer1Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
            answer2Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
            answer3Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
            answer4Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
            //make new sum in playfield and store new correctanswer
            Double newCorrectAnswer = makeSumAndSetSumInPlayFieldForDivision();
            mViewModel.setCorrectAnswerDivDbl(newCorrectAnswer);
            setAnswerButtonsForDivision();
        }
    };


    public void setTheTimerAndProgressbar(int numSec ){

        final long numberOfMiliSeconds = numSec *1000;
        //Log.i("TagFloor"  , "PlayFieldFragment line 223 numberOfMiliseconds is:"  + numberOfMiliSeconds);
        final int maxTime = (int)(long)numberOfMiliSeconds;
        progressBar.setMax(maxTime);
        countDownTimer = new CountDownTimer(numberOfMiliSeconds, 1000) {

            long maxWaarde = numberOfMiliSeconds;
            @Override
            public void onTick(long numberOfMiliSeconds) {
                nrOfSecTxtView.setText(String.valueOf(numberOfMiliSeconds/1000) + " sec");
                progressBar.setProgress((int) (maxWaarde - numberOfMiliSeconds));
            }

            @Override
            public void onFinish() {
                finishGame();
            }

        }.start();
    }

    public void finishGame(){
        ScoreFragment newFragment = new ScoreFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .remove(PlayFieldFragment.this)
                .replace(R.id.container , newFragment)
                .addToBackStack(null)
                .commit();
    }

}