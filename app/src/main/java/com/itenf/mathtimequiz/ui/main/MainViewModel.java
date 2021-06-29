package com.itenf.mathtimequiz.ui.main;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private String typeArithmeticExpression;  //+ of - of *
    private int numberRange = 10;//numbers between 1-10 or 1-100 , default is 1-10
    private int numberOfSeconds = 30; // duration of test: 30, 45, 60 sec, default is 30 sec
    private int firstNumber;
    private int secondNumber;
    private int correctAnswer;//the correct answer of the sum
    private Double correctAnswerDbl;//the correct answer for the division
    private  int score;//the score

    //The Getters and Setters

    public Double getCorrectAnswerDivDbl() { return correctAnswerDbl; }

    public void setCorrectAnswerDivDbl(Double correctAnswerDivDbl) { this.correctAnswerDbl = correctAnswerDivDbl; }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


    public int getNumberRange() {
        return numberRange;
    }

    public void setNumberRange(int numberRange) {
        this.numberRange = numberRange;
    }

    public int getNumberOfSeconds() {
        return numberOfSeconds;
    }

    public void setNumberOfSeconds(int numberOfSeconds) {
        this.numberOfSeconds = numberOfSeconds;
    }

    public int getFirstNumber() { return firstNumber; }

    public void setFirstNumber(int firstNumber) { this.firstNumber = firstNumber; }

    public int getSecondNumber() { return secondNumber; }

    public void setSecondNumber(int secondNumber) { this.secondNumber = secondNumber; }

    public String getTypeArithmeticExpression() {
        return typeArithmeticExpression;
    }

    public void setTypeArithmeticExpression(String typeArithmeticExpression) {
        this.typeArithmeticExpression = typeArithmeticExpression;
    }
}