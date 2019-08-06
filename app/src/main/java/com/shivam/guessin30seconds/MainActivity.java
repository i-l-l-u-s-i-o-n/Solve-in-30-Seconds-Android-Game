package com.shivam.guessin30seconds;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView resultStatus;
    TextView equation;
    TextView scoreStatus;
    TextView timer;
    Button playAgain;
    Button op1;
    Button op2;
    Button op3;
    Button op4;

    ConstraintLayout gameSetupLayout;

    String equationString ;
    int result ;
    int indexOfCorrectAnswer ;
    int score=0;
    int numberOfQuestionsDisplayed=0;
    boolean gameOver=false;
    ArrayList<Integer> options =new ArrayList<>();


    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        gameSetupLayout.setVisibility(View.VISIBLE);
        findViewById(R.id.devStatus).setVisibility(View.INVISIBLE);
        setUpNewQuestion();

        startTimer();
    }


    public void optionPressed(View view){

        if (!gameOver) {
            if (Integer.toString(indexOfCorrectAnswer).equals(view.getTag().toString())) {
                resultStatus.setText("Correct !");
                score++;
            } else
                resultStatus.setText("Wrong! Try again:(");

            numberOfQuestionsDisplayed++;
            resultStatus.setVisibility(View.VISIBLE);
            scoreStatus.setText(score + "/" + numberOfQuestionsDisplayed);
            setUpNewQuestion();
        }

    }

    public void setUpNewQuestion(){



        playAgain.setVisibility(View.INVISIBLE);
        Random random =new Random();

        int equationType=random.nextInt(2);  // Generates Random number 0,1



        if (equationType == 0 ) {
//            a+b*c
            int a=random.nextInt(20)+1;
            int b=random.nextInt(10)+1;
            int c=random.nextInt(10)+1;

            result = a+b*c;
            equationString= a +" + "+ b+ " * "+ c + " = ?";
        }else{
//            a%b-c
            int a=random.nextInt(20)+1;
            int b=random.nextInt(10)+1;
            int c=random.nextInt(10)+1;

            result=a%b-c;
            equationString= a +" % "+ b+ " - "+ c+ " = ?";
        }

        // Set equation to the TextView
        equation.setText(equationString);

        // Now generate the 4 options and add it to List

        indexOfCorrectAnswer=random.nextInt(4);  // Location of correct ans in List.

        options.clear();
        for (int i=0; i<4; i++){

            if (i == indexOfCorrectAnswer)
                options.add(result);

            else{

                int wrongAnsLogic=random.nextInt(2);
                int wrongAnswer;
                if (wrongAnsLogic==0){
                    wrongAnswer =random.nextInt(8)+result;
                    while(wrongAnswer == result || options.contains(wrongAnswer)){
                        wrongAnswer=random.nextInt(8)+result;
                    }
                }else{
                    wrongAnswer =random.nextInt(8)+result;
                    while(wrongAnswer == result || options.contains(wrongAnswer)){
                        wrongAnswer=random.nextInt(8)-result;
                    }
                }

                options.add(wrongAnswer);
            }

        }


        op1.setText(Integer.toString(options.get(0)));
        op2.setText(Integer.toString(options.get(1)));
        op3.setText(Integer.toString(options.get(2)));
        op4.setText(Integer.toString(options.get(3)));

    }


    // Play again Logic!!!!
    public void setPlayAgain(View view){
        score=0;
        numberOfQuestionsDisplayed=0;
        gameOver=false;
        timer.setText("30s");
        playAgain.setVisibility(View.INVISIBLE);
        scoreStatus.setText(score + "/" + numberOfQuestionsDisplayed);
        setUpNewQuestion();
        startTimer();
    }



    //Timer setUp
    private void startTimer(){
        new CountDownTimer(31000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText((millisUntilFinished/1000 - 1) + "s");
            }

            @Override
            public void onFinish() {
                playAgain.setVisibility(View.VISIBLE);
                resultStatus.setVisibility(View.INVISIBLE);
                gameOver=true;
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        op1=findViewById(R.id.option1);
        op2= findViewById(R.id.option2);
        op3= findViewById(R.id.option3);
        op4= findViewById(R.id.option4);

        equation= findViewById(R.id.equation);
        resultStatus=findViewById(R.id.resultStatus);
        resultStatus.setVisibility(View.INVISIBLE);

        startButton=findViewById(R.id.startButton);
        startButton.setVisibility(View.VISIBLE);

        scoreStatus=findViewById(R.id.scoreStatus);
        playAgain = findViewById(R.id.playAgain);

        timer=findViewById(R.id.timer);
        gameSetupLayout=findViewById(R.id.gameSetupLayout);

        gameSetupLayout.setVisibility(View.INVISIBLE);


    }
}
