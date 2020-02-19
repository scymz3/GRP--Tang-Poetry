package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class exerciseWrongPage extends AppCompatActivity {

    private int count; //the number of exercise questions
    private int current; //the question that is working on
    private int questionNum; //the serial number of question
    private static List<Question> list; //the question list
    private Question q;
    private Button btn_changeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_wrong);

        /**
         * goto game page
         */
        Button btn_game = (Button) findViewById(R.id.game);
        btn_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(exerciseWrongPage.this, gamePage.class);
                startActivity(intent);
            }
        });

        /**
         * goto finish page
         */
        btn_changeModel = (Button) findViewById(R.id.changeModel);
        btn_changeModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exerciseResultPage.reviewMode == 0){
                    exerciseResultPage.reviewMode = 1;
                    btn_changeModel.setText("REVIEW ALL QUESTIONS");
                }else{
                    exerciseResultPage.reviewMode = 0;
                    btn_changeModel.setText("REVIEW WRONG QUESTIONS");
                }
                Intent intent = new Intent(exerciseWrongPage.this, exerciseWrongPage.class);
                startActivity(intent);
            }
        });

        exercisePage exercisepage = new exercisePage();
        list = exercisepage.getlist(); //get the question list
        final List<Integer> wrongList = exercisepage.checkAnswer(list);
        if(exerciseResultPage.reviewMode == 0){
            btn_changeModel.setText("REVIEW ALL QUESTIONS");
            count = wrongList.size();
        }else{
            btn_changeModel.setText("REVIEW WRONG QUESTIONS");
            count = list.size();
        }

        current = 0;
        questionNum = 1;

        /**
         * Set the content of question and options
         */
        final TextView questionNumber = findViewById(R.id.noQuestion);
        final TextView question = findViewById(R.id.question);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final RadioButton[] radioButtons = new RadioButton[3];
        radioButtons[0] = findViewById(R.id.answerA);
        radioButtons[1] = findViewById(R.id.answerB);
        radioButtons[2] = findViewById(R.id.answerC);
        final TextView explaination = findViewById(R.id.explaination);
        Button btn_previous = findViewById(R.id.btn_previous);
        Button btn_next = findViewById(R.id.btn_next);

        //assigns a value to the control
        questionNumber.setText(questionNum + "");
        //base on review model to show specific question
        q = getQuestion(wrongList);

        question.setText(q.question);
        radioButtons[0].setText(q.answerA);
        radioButtons[1].setText(q.answerB);
        radioButtons[2].setText(q.answerC);
        explaination.setText("\nExplaination:\n"+q.explaination);

        radioGroup.clearCheck();
        if(q.selectedAnswer != -1){
            radioButtons[q.selectedAnswer].setChecked(true);
        }
        getColor(radioButtons);

        //jump to next question
        btn_next.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                if(current < count -1){
                    questionNum++;
                    current++;
                    //update question and options
                    q = getQuestion(wrongList);
                    questionNumber.setText(questionNum + "");
                    question.setText(q.question);
                    radioButtons[0].setText(q.answerA);
                    radioButtons[1].setText(q.answerB);
                    radioButtons[2].setText(q.answerC);
                    explaination.setText("\nExplaination:\n"+q.explaination);

                    //if the question was selected before, record the option
                    radioGroup.clearCheck();
                    if(q.selectedAnswer != -1){
                        radioButtons[q.selectedAnswer].setChecked(true);
                    }
                    getColor(radioButtons);
                }
                else{
                    new AlertDialog.Builder(exerciseWrongPage.this)
                            .setTitle("Reminder")
                            .setMessage("This is the last question! \nAre you sure you want to quit?")
                            .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(exerciseWrongPage.this, exerciseResultPage.class);
                                    startActivity(intent);
//                                    exercisePage.this.finish();
                                }
                            })
                            .setNegativeButton("Cancel",null)
                            .show();
                }
            }

        });

        //jump tp previous question
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if the question is not the first question, go to previous one; otherwise, do not change
                if (current > 0)
                {
                    questionNum--;
                    current--;
                    q = getQuestion(wrongList);
                    questionNumber.setText(questionNum + "");
                    question.setText(q.question);
                    radioButtons[0].setText(q.answerA);
                    radioButtons[1].setText(q.answerB);
                    radioButtons[2].setText(q.answerC);
                    explaination.setText("\nExplaination:\n"+q.explaination);


                    //if the option has been chosen, record it
                    radioGroup.clearCheck();
                    if (q.selectedAnswer != -1) {
                        radioButtons[q.selectedAnswer].setChecked(true);
                    }
                    getColor(radioButtons);
                }
                else{
                    new AlertDialog.Builder(exerciseWrongPage.this)
                            .setTitle("Reminder")
                            .setMessage("This is the first question.")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //do nothing
                                }
                            }).show();
                }

            }
        });

//        //update the choice of users
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                for (int i = 0; i < 3; i++) {
//                    if (radioButtons[i].isChecked() == true) {
//                        list.get(current).selectedAnswer = i;
//                        //System.out.println("check    list.get(current).selectedAnswer "+list.get(current).selectedAnswer);
//                        break;
//                    }
//                }
//
//            }
//        });

    }

    /**
     * If the model is review wrong questions, only wrong questions are shown.
     * If the model is review all the questions, all the questions are shown.
     *
     * @param wrongList
     * @return
     */
    public Question getQuestion(List<Integer> wrongList){
        Question q;
        if(exerciseResultPage.reviewMode == 0){
            q = list.get(wrongList.get(current));
        }else{
            q = list.get(current);
        }

        return q;
    }

    /**
     * Set correct answer is green; and set the wrong answer that was selected is red
     *
     * @param radioButtons
     */
    public void getColor(RadioButton[] radioButtons){
        for(int i = 0; i < 3; i++){
            radioButtons[i].setTextColor(Color.BLACK);
        }
        if(q.answer != q.selectedAnswer && q.selectedAnswer != -1){
            radioButtons[q.selectedAnswer].setTextColor(android.graphics.Color.RED);
            radioButtons[q.answer].setTextColor(android.graphics.Color.GREEN);

        }else{
            radioButtons[q.answer].setTextColor(android.graphics.Color.GREEN);
        }
    }

}
