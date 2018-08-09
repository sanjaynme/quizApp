package com.np.nast.quizapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;

    Button SaveButtonInSQLite, ShowSQLiteDataInListView;

    String HttpJSonURL = "http://192.168.15.120/sync/quiz.php";

    ProgressDialog progressDialog;
    private String sQLiteDataBaseQueryHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SaveButtonInSQLite = findViewById(R.id.button);

        ShowSQLiteDataInListView = findViewById(R.id.button2);

        SaveButtonInSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDataBaseBuild();

                SQLiteTableBuild();

                DeletePreviousData();

                ApiService.getServiceClass().getAllPost().enqueue(new Callback<List<ApiObject>>() {
                    @Override
                    public void onResponse(Call<List<ApiObject>> call, Response<List<ApiObject>> response) {
                        if (response.isSuccessful()) {
                            for (int i = 0; i < response.body().size(); i++) {
                                sQLiteDataBaseQueryHolder = "INSERT INTO " + SQLiteHelper.TABLE_NAME + " (" +
                                        "question,opta,optb,optc,answer) VALUES('" +
                                        response.body().get(i).getQuestion() + "', '" + response.body().get(i).getOpta()
                                        + "', '" + response.body().get(i).getOptb() + "', '" + response.body().get(i).getOptc() + "', '" + response.body().get(i).getAnswer() + "');";

                                sqLiteDatabase.execSQL(sQLiteDataBaseQueryHolder);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ApiObject>> call, Throwable t) {
                        Log.d("", "Error msg is :::" + t.getMessage());
                    }
                });
            }
        });

        ShowSQLiteDataInListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
                startActivity(intent);

            }
        });


    }

    private class StoreJSonDataInToSQLiteClass extends AsyncTask<Void, Void, Void> {

        public Context context;

        String FinalJSonResult;

        public StoreJSonDataInToSQLiteClass(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("LOADING");
            progressDialog.setMessage("Please Wait");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpServiceClass httpServiceClass = new HttpServiceClass(HttpJSonURL);

            try {
                httpServiceClass.ExecutePostRequest();

                if (httpServiceClass.getResponseCode() == 200) {

                    FinalJSonResult = httpServiceClass.getResponse();

                    if (FinalJSonResult != null) {

                        JSONArray jsonArray = null;
                        try {

                            jsonArray = new JSONArray(FinalJSonResult);
                            JSONObject jsonObject;

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);
                                String tempQuetion = jsonObject.getString("question");
                                String tempopta = jsonObject.getString("opta");
                                String tempoptb = jsonObject.getString("optb");
                                String tempoptc = jsonObject.getString("optc");
                                String tempanswer = jsonObject.getString("answer");

                                //  String tempSubjectName = jsonObject.getString("SubjectName");
//
//                                String tempSubjectFullForm = jsonObject.getString("SubjectFullForm");

                                String SQLiteDataBaseQueryHolder = "INSERT INTO " + SQLiteHelper.TABLE_NAME + " (question,opta,optb,optc,answer) VALUES('" + tempQuetion + "', '" + tempopta + "', '" + tempoptb + "', '" + tempoptc + "', '" + tempanswer + "');";

                                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);

                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {

                    Toast.makeText(context, httpServiceClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            sqLiteDatabase.close();

            progressDialog.dismiss();

            Toast.makeText(MainActivity.this, "Load Done", Toast.LENGTH_LONG).show();

        }
    }


    public void SQLiteDataBaseBuild() {

        sqLiteDatabase = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild() {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "(" + SQLiteHelper.Table_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table_Column_1_question + " VARCHAR, " + SQLiteHelper.Table_Column_2_opta + " VARCHAR, " + SQLiteHelper.Table_Column_3_optb + " VARCHAR, " + SQLiteHelper.Table_Column_4_optc + " VARCHAR, " + SQLiteHelper.Table_Column_5_answer + " VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME_GK + "(" + SQLiteHelper.Table_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table_Column_1_question + " VARCHAR, " + SQLiteHelper.Table_Column_2_opta + " VARCHAR, " + SQLiteHelper.Table_Column_3_optb + " VARCHAR, " + SQLiteHelper.Table_Column_4_optc + " VARCHAR, " + SQLiteHelper.Table_Column_5_answer + " VARCHAR)");

    }

    public void DeletePreviousData() {

        sqLiteDatabase.execSQL("DELETE FROM " + SQLiteHelper.TABLE_NAME + "");

    }
}