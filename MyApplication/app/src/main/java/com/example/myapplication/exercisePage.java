package com.example.myapplication;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import com.example.greendao.DaoSession;
import com.example.greendao.Question;
import com.example.greendao.QuestionDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class exercisePage extends AppCompatActivity {
    private QuestionDao questionDao;

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

        DaoSession daoSession = PoemList.getDaoSession();
        questionDao = daoSession.getQuestionDao();

        initData();

        list = getQuestion(); //get the question list from database
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

        //assigns a value to the control
        questionNumber.setText(questionNum + "");
        Question q = list.get(0);
        question.setText(q.getQuestion());
        radioButtons[0].setText(q.getAnswerA());
        radioButtons[1].setText(q.getAnswerB());
        radioButtons[2].setText(q.getAnswerC());

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
                    question.setText(q.getQuestion());
                    radioButtons[0].setText(q.getAnswerA());
                    radioButtons[1].setText(q.getAnswerB());
                    radioButtons[2].setText(q.getAnswerC());

                    //if the question was selected before, record the option
                    radioGroup.clearCheck();
                    if(q.getSelectedAnswer() != -1){
                        radioButtons[q.getSelectedAnswer()].setChecked(true);
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
                    question.setText(q.getQuestion());
                    radioButtons[0].setText(q.getAnswerA());
                    radioButtons[1].setText(q.getAnswerB());
                    radioButtons[2].setText(q.getAnswerC());

                    //if the option has been chosen, record it
                    radioGroup.clearCheck();
                    if (q.getSelectedAnswer() != -1) {
                        radioButtons[q.getSelectedAnswer()].setChecked(true);
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
                        list.get(current).setSelectedAnswer(i);
                        break;
                    }
                }

            }
        });

    }

    protected void initData() {
        questionDao.deleteAll();
        readFromFile();
    }

    private void readFromFile() {
        AssetManager assetManager = getAssets();
        int id = 0;
        try {
            InputStream inputStream = assetManager.open("exerciseQuestion.txt");
            if (inputStream != null) {
                InputStreamReader inputReader = new InputStreamReader(inputStream);
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line;
                //分行读取
                buffReader.readLine();
                while ((line = buffReader.readLine()) != null) {
                    String[] strArr = line.split(":");
                    Question exerciseQuestion = new Question(id,strArr[0],strArr[1],strArr[2],strArr[3],Integer.parseInt(strArr[4]),strArr[5],-1);
                    questionDao.insert(exerciseQuestion);
                    id++;
                }
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Question> getQuestion(){
        List<Question> lists = new ArrayList<>();
        //get the number of exercise question in database
        int totalCount = (int) questionDao.count();

        for(int i = 0; i < 10; i++){
            int ranNum = (int) (Math.random() * (totalCount-1));
            int j = 0;
            int size = lists.size();
            while(j < size){
                if(lists.get(j).getID() == ranNum){
                    ranNum = (int) (Math.random() * (totalCount-1));
                    j = 0;
                }else {
                    j++;
                }
            }
            QueryBuilder qb = questionDao.queryBuilder();
            qb.where(QuestionDao.Properties.ID.eq(ranNum));
            List<Question> ques = qb.list();
            //System.out.println("ques "+ ques.get(0).getQuestion() + "  "+ques.get(0).getID());
            if(ques.get(0) != null) {
                lists.add(ques.get(0));
            }
        }
        return lists;
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
            if(list.get(i).getAnswer() != list.get(i).getSelectedAnswer()){
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
