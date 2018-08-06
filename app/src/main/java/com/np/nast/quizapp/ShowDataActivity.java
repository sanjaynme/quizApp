package com.np.nast.quizapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowDataActivity extends AppCompatActivity {

    int score = 0;
    int quid = 0;


    TextView txtQuestion;
    RadioButton rda, rdb, rdc;
    Button butNext;


    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListAdapter listAdapter;
    ListView LISTVIEW;
    ArrayList<String> ID_Array;
    ArrayList<String> QUESTION_Array;
    ArrayList<String> OPTA_Array;
    ArrayList<String> OPTB_Array;
    ArrayList<String> OPTC_Array;
    ArrayList<String> ANSWER_Array;
    ArrayList<String> ListViewClickItemArray = new ArrayList<String>();
    private List<Question> questionList;
    private Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        SQLiteHelper sqLiteHelper = new SQLiteHelper(this);

        questionList = sqLiteHelper.getData();
        Collections.shuffle(questionList);
        currentQuestion = questionList.get(quid);

        txtQuestion = findViewById(R.id.question);
        rda = findViewById(R.id.radio0);
        rdb = findViewById(R.id.radio1);
        rdc = findViewById(R.id.radio2);
        butNext = findViewById(R.id.button1);
        setQuestionView();
    }

    private void setQuestionView() {
        txtQuestion.setText(currentQuestion.getQuestion());
        rda.setText(currentQuestion.getOptA());
        rdb.setText(currentQuestion.getOptB());
        rdc.setText(currentQuestion.getOptC());
        quid++;
    }

    public void btClick(View view) {
        RadioGroup grp = findViewById(R.id.radioGroup1);
        RadioButton answer = findViewById(grp.getCheckedRadioButtonId());
        if (currentQuestion.getAnswer().equals(answer.getText())) {
            Toast.makeText(ShowDataActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
            score++;
            Log.d("Score", "Your score: " + score);
        } else {
            Toast.makeText(ShowDataActivity.this, "You Are Wrong" + "The Correct Answer is:" + currentQuestion.getAnswer(), Toast.LENGTH_SHORT).show();
        }

        if (quid <5) {
            currentQuestion = questionList.get(quid);
            setQuestionView();
        } else {
            Intent intent = new Intent(ShowDataActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
    }
}