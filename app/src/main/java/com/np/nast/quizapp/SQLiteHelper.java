package com.np.nast.quizapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.np.nast.quizapp.Question;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME = "SubjectDataBase";

    public static final String TABLE_NAME = "quiz";

    public static final String Table_Column_ID = "id";

    public static final String Table_Column_1_question = "question";

    public static final String Table_Column_2_opta = "opta";
    public static final String Table_Column_3_optb = "optb";
    public static final String Table_Column_4_optc = "optc";
    public static final String Table_Column_5_answer = "answer";
    private SQLiteDatabase dbase;
    private int rowCount = 0;

    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        dbase = database;
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + Table_Column_ID + " INTEGER PRIMARY KEY, "
                + Table_Column_1_question + " VARCHAR, "
                + Table_Column_2_opta + " VARCHAR,"
                + Table_Column_3_optb + " VARCHAR, "
                + Table_Column_4_optc + " VARCHAR, "
                + Table_Column_5_answer + "VARCHAR"
                + ")";
        database.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public List<Question> getData() {
        dbase = this.getReadableDatabase();
        String[] columns = {Table_Column_ID, Table_Column_1_question, Table_Column_2_opta, Table_Column_3_optb,
                Table_Column_4_optc, Table_Column_5_answer};
        Cursor cursor = dbase.query(TABLE_NAME, columns, null, null, null, null, null);
        List<Question> questionList = new ArrayList<Question>();

        while (cursor.moveToNext()) {
            Question q = new Question();
            q.setId(cursor.getInt(0));
            q.setQuestion(cursor.getString(1));
            q.setAnswer(cursor.getString(2));
            q.setOptA(cursor.getString(3));
            q.setOptB(cursor.getString(4));
            q.setOptC(cursor.getString(5));

            questionList.add(q);
        }
        return questionList;
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<Question>();

        dbase = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        rowCount = cursor.getCount();

        if (cursor.moveToFirst()) {
            do {
                Question q = new Question();
                q.setId(cursor.getInt(0));
                q.setQuestion(cursor.getString(1));
                q.setAnswer(cursor.getString(2));
                q.setOptA(cursor.getString(3));
                q.setOptB(cursor.getString(4));
                q.setOptC(cursor.getString(5));

                questionList.add(q);

            } while (cursor.moveToNext());
        }
        return questionList;
    }

}