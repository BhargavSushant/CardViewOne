package com.attosectechnolabs.cardviewone;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionPaper extends AppCompatActivity {

    String sQP_Code;
    public SQLiteDatabase db;
    public ProgressBar progressBar;
    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;
    List<GetDataAdapter> GetDataAdapter1;


    String id ,Question_id ,QP_Code ,Question ,OptA ,OptB ,OptC ,OptD,Answer;
    String GET_JSON_DATA_HTTP_URL = "http://attosectechnolabs.com/Projects/eduapp/getAllQuestions.php";

    String JSON_ID="id";
    String JSON_Question_id="Question_id";
    String JSON_QP_Code = "QP_Code";
    String JSON_Question  = "Question";
    String JSON_OptA ="OptA";
    String JSON_OptB = "OptB";
    String JSON_OptC = "OptC";
    String JSON_OptD = "OptD";
    String JSON_Answer = "Answer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_paper);
        setTitle("ITI Question Paper");
        Intent intent = getIntent();

        sQP_Code = intent.getStringExtra("QP_Code1");
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        GetDataAdapter1 = new ArrayList<>();


        // create database for question_table if doesn't exists
        createDatabase();

        // for QP_Code check if Downloaded column in Question paper code table is 0 or 1
       if(checkIfPaperExists(sQP_Code) ==  0) {
          downloadPaperAndUpdateSQLiteDB();  // download paper
          updateQuizCode();                  // update QuizCode DOWNLOADED = 1 for respective QP_Code
       }

        readFromSQLiteDBAndCreateCards();   // generate card for questions.
    }


    private void downloadPaperAndUpdateSQLiteDB() {

        System.out.println("###############################");
        JSON_DATA_WEB_CALL();

    }

    private void readFromSQLiteDBAndCreateCards() {
    }

    private void updateQuizCode() {
    }

    protected void createDatabase(){
        db=openOrCreateDatabase("QuizCode", this.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS question_table " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "Question_id VARCHAR UNIQUE, QP_Code VARCHAR," +
                "Question VARCHAR , OptA VARCHAR," +
                "OptB VARCHAR , OptC VARCHAR," +
                "OptD VARCHAR , Answer VARCHAR" +
                ");");
    }

    protected void insertIntoDB(String sQP_Code){
        String QP_Code = sQP_Code;
        db=openOrCreateDatabase("QuizCode", this.MODE_PRIVATE, null);
        String query = "INSERT OR IGNORE INTO question_table " +
                "(id ,Question_id ,QP_Code ,Question ,OptA ,OptB ,OptC ,OptD,Answer ) " +
                "VALUES('"+id+"', '"+Question_id+"', '"+QP_Code+"',, '"+Question+"', '"+OptA+"', '"+OptB+"', '"+OptC+"', '"+OptD+"', '"+Answer+"');";
        db.execSQL(query);
    }

    protected Integer checkIfPaperExists(String sQP_Code){

        Integer result = 0;
        String query = "SELECT DOWNLOADED FROM QuizCode WHERE QP_Code = ?";
        db=openOrCreateDatabase("QuizCode", this.MODE_PRIVATE, null);

        Cursor csr = db.rawQuery(query, new String[] {sQP_Code});

        if (csr != null ){

            if  (csr.moveToFirst()) {
                do {
                    result = csr.getInt(csr.getColumnIndex("DOWNLOADED"));
                }while (csr.moveToNext());
            }

        }
        csr.close();
        return result;

    }

    public void JSON_DATA_WEB_CALL(){

        String GET_JSON_DATA_HTTP_URL2 = GET_JSON_DATA_HTTP_URL+"?QP_Code="+sQP_Code;
System.out.println(GET_JSON_DATA_HTTP_URL2);
        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL2,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                     //   progressBar.setVisibility(View.GONE);
                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            GetDataAdapter GetDataAdapter2 = new GetDataAdapter();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setId(json.getInt(JSON_ID));
                GetDataAdapter2.setQuestion	(json.getString	(	JSON_Question	));
                GetDataAdapter2.setQP_Code	(json.getString	(	JSON_QP_Code	));
                GetDataAdapter2.setQuestion_id (json.getString	(	JSON_Question_id	));
                GetDataAdapter2.setOptA	(json.getString	(	JSON_OptA	));
                GetDataAdapter2.setOptB	(json.getString	(	JSON_OptB	));
                GetDataAdapter2.setOptC	(json.getString	(	JSON_OptC	));
                GetDataAdapter2.setOptD	(json.getString	(	JSON_OptD	));
                GetDataAdapter2.setAnswer(json.getString(	JSON_Answer	));
            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

     //   recyclerViewadapter = new RVAdapterPosts(GetDataAdapter1, this);
     //   recyclerView2.setAdapter(recyclerViewadapter);
    }

}
