package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class exercisePage extends AppCompatActivity {

    private int count; //the number of exercise questions
    private int current; //the question that is working on
    private int questionNum; //the serial number of question
    private static List<Question> list; //the question list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise);

        /**
         * goto game page
         */
        Button btn_game = (Button)findViewById(R.id.game);
        btn_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(exercisePage.this, gamePage.class);
                startActivity(intent);
            }
        });

        /**
         * goto finish page
         */
        Button btn_finish = (Button) findViewById(R.id.finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(exercisePage.this, exerciseResultPage.class);
                startActivity(intent);
            }
        });

        DBService dbService = new DBService();
        list = dbService.getQuestion(); //get the question list from database
        count = list.size();
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
        Button btn_previous = findViewById(R.id.btn_previous);
        Button btn_next = findViewById(R.id.btn_next);

        //final TextView tv_explaination = findViewById(R.id.explaination);

        //assigns a value to the control
        questionNumber.setText(questionNum + "");
        Question q = list.get(0);
        question.setText(q.question);
        //tv_explaination.setText(q.explaination);
        radioButtons[0].setText(q.answerA);
        radioButtons[1].setText(q.answerB);
        radioButtons[2].setText(q.answerC);

        //jump to next question
        btn_next.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                if(current < count -1){
                    questionNum++;
                    current++;
                    //update question and options
                    questionNumber.setText(questionNum + "");
                    Question q = list.get(current);
                    question.setText(q.question);
                    //tv_explaination.setText(q.explaination);
                    radioButtons[0].setText(q.answerA);
                    radioButtons[1].setText(q.answerB);
                    radioButtons[2].setText(q.answerC);

                    //if the question was selected before, record the option
                    radioGroup.clearCheck();
                    if(q.selectedAnswer != -1){
                        radioButtons[q.selectedAnswer].setChecked(true);
                    }
                }
                else{
                    new AlertDialog.Builder(exercisePage.this)
                            .setTitle("Reminder")
                            .setMessage("This is the last question! \nAre you sure you want to submit your answers?")
                            .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(exercisePage.this, exerciseResultPage.class);
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
                    Question q = list.get(current);
                    questionNumber.setText(questionNum + "");
                    question.setText(q.question);
                    radioButtons[0].setText(q.answerA);
                    radioButtons[1].setText(q.answerB);
                    radioButtons[2].setText(q.answerC);
                    //tv_explaination.setText(q.explaination);


                    //if the option has been chosen, record it
                    radioGroup.clearCheck();
                    if (q.selectedAnswer != -1) {
                        radioButtons[q.selectedAnswer].setChecked(true);
                    }
                }
                else{
                    new AlertDialog.Builder(exercisePage.this)
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

        //update the choice of users
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                for (int i = 0; i < 3; i++) {
                    if (radioButtons[i].isChecked() == true) {
                        list.get(current).selectedAnswer = i;
                        //System.out.println("check    list.get(current).selectedAnswer "+list.get(current).selectedAnswer);
                        break;
                    }
                }

            }
        });

    }

    /**
     * Check whether the answer is correct
     *
     * @param list
     * @return
     */
    public List<Integer> checkAnswer(List<Question> list){
        List<Integer> wrongList = new ArrayList<Integer>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).answer != list.get(i).selectedAnswer){
                wrongList.add(i);
            }
        }
        return wrongList;
    }

    /**
     * Get the list
     *
     * @return list - question list
     */
    public List<Question> getlist(){
        return list;
    }
}
