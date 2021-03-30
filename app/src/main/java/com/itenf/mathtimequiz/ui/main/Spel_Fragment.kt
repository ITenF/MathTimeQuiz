package com.itenf.mathtimequiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.itenf.mathtimequiz.ui.main.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Spel_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
abstract class Spel_Fragment : Fragment()  {

    lateinit var mViewModel:MainViewModel

    abstract var firstNumberTxtView:TextView
    abstract var secondNumberTxtView:TextView
    abstract var arithmeticExpTxtView:TextView
    abstract var scoreTxtView:TextView
    abstract var answer1Button:TextView
    abstract var answer2Button:TextView
    abstract var answer3Button:TextView
    abstract var answer4Button:TextView
    abstract var progressBar:ProgressBar
    abstract var nrOfSecTxtView:TextView



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spel_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //to make connection to Viewmodel
        mViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        //find all views
        nrOfSecTxtView = view.findViewById<TextView>(R.id.nrOfSecTxtView)

        progressBar = view.findViewById<ProgressBar>(R.id.timeProgressBar)
        firstNumberTxtView = view.findViewById<TextView>(R.id.EditTextNumber1)
        secondNumberTxtView = view.findViewById<TextView>(R.id.editTextNumber2)
        arithmeticExpTxtView = view.findViewById<TextView>(R.id.arithmeticExprTxtbox)
        scoreTxtView = view.findViewById<TextView>(R.id.scoreTxtView)
        answer1Button = view.findViewById<Button>(R.id.answer1Btn)
        answer2Button = view.findViewById<Button>(R.id.answer2Btn)
        answer3Button = view.findViewById<Button>(R.id.answer3Btn)
        answer4Button = view.findViewById<Button>(R.id.answer4Btn)
    }

    fun getRandomNumber(maxValue: Int) : Int{
        var min = 1
        var randNumber = Math.random() * (maxValue - min + 1) + min
        return randNumber.toInt()
    }

    fun makeSumAndSetSumInPlayField(typeOfArithmeticExpr:String){
        var firstNumber = getRandomNumber(mViewModel.numberRange)

    }

    fun checkAnswer(buttonWithNumber: Button){
        var  buttonNumber = buttonWithNumber.text
        Log.d("TagFloor", "Spel_Fragment regel 58 de tekst op de antwoordknop is: " + buttonNumber)
        if (buttonNumber.equals(mViewModel.correctAnswer)){
            //raise the score with 1
                var newCorrectAnswer  = mViewModel.correctAnswer + 1
                mViewModel.correctAnswer(newCorrectAnswer)

        }
    }

    private operator fun Int.invoke(newCorrectAnswer: Int) {
    }

}

