package com.itenf.mathtimequiz.ui.main;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
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
    NavController navController;
    private int answerInt;
    private int firstNumber;
    private int secondNumber;
    private int falseAnswer;
    private String typeOfArithmeticExpr;
    private int nrButton;
    private int nrRightAnswerButton;
    ArrayList<Button> buttonArrayList;
    ArrayList <Integer> integerList1of4;
    ArrayList<Integer> newAnswerArrayList;
    private String falseText;



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
        //set type of sums
        typeOfArithmeticExpr = mViewModel.getTypeArithmeticExpression();

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
        makeSumAndSetSumInPlayFieldWorkerThread();
        setAnswerButtons();
        setTheTimerAndProgressbar(mViewModel.getNumberOfSeconds());
    }

    //create a random number between the range that is chosen 1-10 or 1-200
    public int getRandomNumber( int maxValue){
        int min = 1;
        int randNumber = (int)(Math.random() * (maxValue - min + 1) + min);
       return randNumber;
    }


    //make a new sum from the chosen artithmetic expression in a extra worker thread,and set  the textfields of the playfield and save the correct answer in answerInt
    public void makeSumAndSetSumInPlayFieldWorkerThread(){
        //Do this in extra worker thread and not in main thread, to save CPU
            new Thread(new Runnable() {
            @Override
            public void run() {
                //make the first number of the sum
                firstNumber = getRandomNumber(mViewModel.getNumberRange());
                //make the second number of the sum
                secondNumber = getRandomNumber(mViewModel.getNumberRange());
                //look what the correct answer of the sum is
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

                //set textViews, this has to be done in the main thread, because it changes the UI
                firstNumberTxtView.post(new Runnable() {
                    @Override
                    public void run() {
                        firstNumberTxtView.setText(String.valueOf(firstNumber));
                    }
                });
                //set type of sum
                arithmeticExpTxtView.post(new Runnable() {
                    @Override
                    public void run() {
                        arithmeticExpTxtView.setText(typeOfArithmeticExpr);
                    }
                });
                secondNumberTxtView.post(new Runnable() {
                    @Override
                    public void run() {
                        secondNumberTxtView.setText(String.valueOf(secondNumber));
                    }
                });
                // !!!you make the division   (firstnumber*secondnumber)  / firstnumber = secondnumber  In this way you make sure there is a integer as an answerInt
                if (typeOfArithmeticExpr.equals("/")){

                    firstNumberTxtView.post(new Runnable() {
                        @Override
                        public void run() {
                            int newFirstNumber = firstNumber*secondNumber;
                            firstNumberTxtView.setText(String.valueOf(newFirstNumber));
                        }
                    });
                    secondNumberTxtView.post(new Runnable() {
                        @Override
                        public void run() {
                            secondNumberTxtView.setText(String.valueOf(firstNumber));
                        }
                    });
                    answerInt = secondNumber;

                }
            }
        }).start();
       
    }


    //make a new sum from the chosen artithmetic expression, but don't set it in the textfields of the playfield, this is used to make the false answer buttons in setAnswerButtons()
    public void makeSum(){
        //make the first number of the sum
        int firstNumber = getRandomNumber(mViewModel.getNumberRange());
        //make the second number of the sum
        int secondNumber = getRandomNumber(mViewModel.getNumberRange());
        //look what the correct answer of the sum is

        if (typeOfArithmeticExpr.equals("+")){
           falseAnswer = firstNumber + secondNumber;
        }
        if (typeOfArithmeticExpr.equals("-")){
            //answerInt = firstNumber - secondNumber;
            falseAnswer = getRandomNumber(mViewModel.getNumberRange());

        }
        if (typeOfArithmeticExpr.equals("*")){
            falseAnswer = firstNumber * secondNumber;
        }
        if (typeOfArithmeticExpr.equals("/")){
            // you make the division   (firstnumber*secondnumber)  / firstnumber = secondnumber  In this way you make sure there is a integer as an answerInt
            int newFirstNumber = firstNumber*secondNumber;
            //firstNumberTxtView.setText(String.valueOf(newFirstNumber));
            falseAnswer = newFirstNumber/firstNumber;
        }

    }



    //set right anwser in one of the 4 buttons en make the text of the other buttons randomly different from the right answer
    public void setAnswerButtons(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //make 3 false answers and save them in a array
               newAnswerArrayList = new ArrayList<>();
                for(int i=0 ; i<3 ; i++) {
                    makeSum();//set the falseAnswer
                    //check if newAnswer is not the same as the correct answer or already is made as an anser or is smaller then the correctanswer-10   or is bigger then the correct answer +10
                    while (falseAnswer == answerInt || newAnswerArrayList.contains(falseAnswer) || falseAnswer < answerInt-10 || falseAnswer > answerInt+10) {
                        makeSum();
                    }
                    //add the new falseAnswer to the array
                    newAnswerArrayList.add(falseAnswer);
                }

                //make an array of the 4 answerbuttons
                buttonArrayList = new ArrayList<>();
                buttonArrayList.add(answer1Button);
                buttonArrayList.add(answer2Button);
                buttonArrayList.add(answer3Button);
                buttonArrayList.add(answer4Button);

                //make a ArrayList of 4 integers with values 1,2,3,4
                //ArrayList is necesarry to make random nr's for the buttons in setAnswers()
                 integerList1of4 = new ArrayList<>(4);
                //fill the ArrayList met numbers 1 t/m 4
                for (int i=0 ; i<4 ; i++){
                    integerList1of4.add(i);
                }
                //Shuffle the numbers in the arrayList to get a ramdom order of 4 :
                Collections.shuffle(integerList1of4);

                //set for each of the answerButton the answerText. The button that is the first number in the array gets the right answer
                nrRightAnswerButton = integerList1of4.get(0);
                buttonArrayList.get(nrButton).post(new Runnable() {
                    @Override
                    public void run() {
                        buttonArrayList.get(nrRightAnswerButton).setText(String.valueOf(answerInt));
                    }
                });
                //for the other buttons with the false answers:
                buttonArrayList.get(integerList1of4.get(1)).post(new Runnable() {
                    @Override
                    public void run() {
                        falseText = String.valueOf(newAnswerArrayList.get(0));
                        buttonArrayList.get(integerList1of4.get(1)).setText(falseText);
                    }
                });
                //nrButton = integerList1of4.get(2);
                buttonArrayList.get(integerList1of4.get(2)).post(new Runnable() {
                    @Override
                    public void run() {
                        falseText = String.valueOf(newAnswerArrayList.get(1));
                        buttonArrayList.get(integerList1of4.get(2)).setText(falseText);
                    }
                });
                //nrButton = integerList1of4.get(3);
                buttonArrayList.get(integerList1of4.get(3)).post(new Runnable() {
                    @Override
                    public void run() {
                        falseText = String.valueOf(newAnswerArrayList.get(2));
                        buttonArrayList.get( integerList1of4.get(3)).setText(falseText);
                    }
                });
           }
        }).start();
    }


//Checks if the answer on the button is the correct answer that is stored in the mViewmodel
    public void checkAnswer( Button buttonWithNummber){
        //get the number of the button
        int buttonNumber = Integer.parseInt(String.valueOf(buttonWithNummber.getText()));
        //the answer is not correct so turn the background color of the button red
        if (buttonNumber ==answerInt){
            //raise the score with 1
            mViewModel.setScore(mViewModel.getScore()+1);
            scoreTxtView.setText(String.valueOf(mViewModel.getScore()));
            //set the background of the right button to green
            buttonWithNummber.setBackgroundColor(getActivity().getResources().getColor(R.color.greenForRightAnswer));
            //buttonWithNummber.getBackground().setBack(getActivity().getResources().getColor(R.color.greenForRightAnswer));
            //wait for 1 second to see the green color to show that the answer was right
            Handler h = new Handler();
            h.postDelayed(r, 250); // <-- the "250" is the delay time in miliseconds to be able to see the color change before the new sum appears.
        }else{
            buttonWithNummber.setBackgroundColor(getActivity().getResources().getColor(R.color.redForWrongAnswer));
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
            
            Log.i("TagFloor"  , "PlayFieldFragment line 254 Runnable is started");
            //remove the backgroundcolor of all the buttons
           //answer1Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
            answer1Button.setBackgroundColor(getActivity().getResources().getColor(R.color.primaryColor));
            //answer2Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
            answer2Button.setBackgroundColor(getActivity().getResources().getColor(R.color.primaryColor));
            //answer3Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
            answer3Button.setBackgroundColor(getActivity().getResources().getColor(R.color.primaryColor));
            //answer4Button.setBackgroundResource(R.color.neutralAnswerButtonColor);
            answer4Button.setBackgroundColor(getActivity().getResources().getColor(R.color.primaryColor));

            //make new sum in playfield and store new correctanswer
            //int newCorrectAnswer = makeSumAndSetSumInPlayField(mViewModel.getTypeArithmeticExpression());
            makeSumAndSetSumInPlayFieldWorkerThread();
            mViewModel.setCorrectAnswer(answerInt);
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
        //if the PlayFieldFragment is not active anymore then the score_fragment should not be constructed
        if (this.isVisible()) {
            //go to Score Fragment
            Navigation.findNavController(view).navigate(R.id.action_playFieldFragment_to_scoreFragment);
        }
    }

}