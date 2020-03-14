package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.greendao.Question;

import java.util.List;

/**
 * Display the number of wrong questions and currency of exercise;
 * Users can review all the questions and wrong questions;
 * Users can restart the exercise.
 */
public class exerciseResultPage extends AppCompatActivity {

    private int count; //the number of exercise questions
    private List<Question> list; //the question list
    private double accuracy; //the accuracy of the exercise
    public static int reviewMode; //0 for review wrong questions; 1 for review all the questions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_result);

        final TextView accuracyTxt = findViewById(R.id.accuracy);
        final TextView numOfWrongTxt = findViewById(R.id.numOfWrong);

        //calculate accuracy
        exercisePage exercisepage = new exercisePage();
        list = exercisepage.getlist(); //get the question list
        count = list.size();

        //told user the number of correct answer and wrong answer
        final List<Integer> wrongList = exercisepage.checkAnswer(list);
        if(wrongList.size()==0){
            accuracyTxt.setText("100 %");
            numOfWrongTxt.setText("( The number of wrong answer is: 0 )");
        }else{
            accuracy = ((double)(count - wrongList.size())/count)*100;
            accuracyTxt.setText((int)Math.rint(accuracy) + " %");
            numOfWrongTxt.setText("( The number of wrong answer is: " + wrongList.size() +" )");
        }


        /**
         * goto exercise page
         */
        Button btn_tryAgain = (Button)findViewById(R.id.tryAgain);
        btn_tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(exerciseResultPage.this,exercisePage.class);
                startActivity(intent);
            }
        });

        /**
         * goto view wrong questions page
         */
        Button btn_exerciseWrong = (Button)findViewById(R.id.wrongQuestion);
        btn_exerciseWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewMode = 0;
                Intent intent = new Intent(exerciseResultPage.this,exerciseWrongPage.class);
                startActivity(intent);
            }
        });

        /**
         * goto view all the questions page
         */
        Button btn_exerciseAll = (Button)findViewById(R.id.reviewQuestion);
        btn_exerciseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewMode = 1;
                Intent intent = new Intent(exerciseResultPage.this,exerciseWrongPage.class);
                startActivity(intent);
            }
        });

        /**
         * goto home page
         */
        Button btn_home = (Button)findViewById(R.id.home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(exerciseResultPage.this,homePage.class);
                startActivity(intent);
            }
        });

        /**
         * goto game interface
         */
        Button btn_game = (Button)findViewById(R.id.game);
        btn_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(exerciseResultPage.this,gamePage.class);
                startActivity(intent);
            }
        });

        /**
         * goto me interface
         */
        Button btn_me = (Button)findViewById(R.id.me);
        btn_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(exerciseResultPage.this,personalCenterPage.class);
                startActivity(intent);
            }
        });


    }
}
