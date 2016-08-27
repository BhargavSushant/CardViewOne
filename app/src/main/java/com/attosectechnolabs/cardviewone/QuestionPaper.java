package com.attosectechnolabs.cardviewone;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


    Context context;
    String sQP_Code;
    public SQLiteDatabase db;
    public ProgressBar progressBar;
    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;
    List<GetDataAdapter> GetDataAdapter1;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;
    QuestionPaperAdapter questionPaperAdapter;
    RecyclerView recyclerView;

    String id ,Question_id ,QP_Code ,Question ,OptA ,OptB ,OptC ,OptD,Answer;
    String GET_JSON_DATA_HTTP_URL = "http://attosectechnolabs.com/Projects/eduapp/getAllQuestions.php";

    public Integer JSON_ID;
    public String JSON_Question_id="Question_id";
    public String JSON_QP_Code = "QP_Code";
    public String JSON_Question  = "Question";
    public String JSON_OptA ="OptA";
    public String JSON_OptB = "OptB";
    public String JSON_OptC = "OptC";
    public String JSON_OptD = "OptD";
    public String JSON_Answer = "Answer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question_paper);
        setTitle("ITI Question Paper");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView4);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        Intent intent = getIntent();

        sQP_Code = intent.getStringExtra("QP_Code1");
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        GetDataAdapter1 = new ArrayList<>();


        // create database for question_table if doesn't exists
        createDatabase();

        // for QP_Code check if Downloaded column in Question paper code table is 0 or 1
       if(checkIfPaperExists(sQP_Code) ==  0) {
          downloadPaperAndUpdateSQLiteDB();  // download paper
          updateQuizCode(sQP_Code);          // update QuizCode DOWNLOADED = 1 for respective QP_Code

       }
      //  context = getApplicationContext();
      // new LoadQP2(sQP_Code,context);

        readFromSQLiteDBAndCreateCards(sQP_Code);   // generate card for questions.


    }




    private void downloadPaperAndUpdateSQLiteDB() {

        JSON_DATA_WEB_CALL();

    }

    private void readFromSQLiteDBAndCreateCards(String sQP_Code) {

        System.out.println("REACHED HERE 2");

        String query = "SELECT * FROM question_table WHERE QP_Code = ?";
        db=openOrCreateDatabase("QuizCode", this.MODE_PRIVATE, null);
        Integer id1;
        String Question_id1;
        String QP_Code1;
        String Question1;
        String OptA1;
        String OptB1;
        String OptC1;
        String OptD1;
        String Answer1;


        Cursor csr = db.rawQuery(query, new String[] {sQP_Code});

        if (csr != null ){

            if  (csr.moveToFirst()) {
                do {
                    GetDataAdapter GetDataAdapter2 = new GetDataAdapter();

                    id1 = csr.getInt(0);
                    Question_id1 = csr.getString(1);
                    QP_Code1 = csr.getString(2);
                    Question1 = csr.getString(3);
                    OptA1 = csr.getString(4);
                    OptB1 = csr.getString(5);
                    OptC1 = csr.getString(6);
                    OptD1 = csr.getString(7);
                    Answer1 = csr.getString(8);

                    GetDataAdapter2.setQP_Id(id1);
                    GetDataAdapter2.setQuestion_id(Question_id1);
                    GetDataAdapter2.setQP_Code(QP_Code1);
                    GetDataAdapter2.setQuestion(Question1);
                    GetDataAdapter2.setOptA(OptA1);
                    GetDataAdapter2.setOptB(OptB1);
                    GetDataAdapter2.setOptC(OptC1);
                    GetDataAdapter2.setOptD(OptD1);
                    GetDataAdapter2.setAnswer(Answer1);

                    GetDataAdapter1.add(GetDataAdapter2);

                }while (csr.moveToNext());
            }

        }
        csr.close();
        db.close();
        System.out.println("REACHED HERE 3");

        recyclerViewadapter = new QuestionPaperAdapter(GetDataAdapter1, this);
       recyclerView.setAdapter(recyclerViewadapter);
        System.out.println("REACHED HERE 4");

    }

    private void updateQuizCode(String sQP_Code) {

        db=openOrCreateDatabase("QuizCode", this.MODE_PRIVATE, null);
        db.rawQuery("UPDATE QuizCode SET DOWNLOADED = 1 WHERE QP_Code = ?", new String []{sQP_Code});
        db.close();

    }

    protected void createDatabase(){
        db=openOrCreateDatabase("QuizCode", this.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS question_table " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "Question_id VARCHAR UNIQUE , QP_Code VARCHAR," +
                "Question VARCHAR , OptA VARCHAR," +
                "OptB VARCHAR , OptC VARCHAR," +
                "OptD VARCHAR , Answer VARCHAR" +
                ");");
        db.close();

    }


    protected void insertIntoDB(Integer JSON_ID,
                                String JSON_QP_Code,
                                String JSON_Question,
                                String JSON_Question_id,
                                String JSON_OptA, String JSON_OptB, String JSON_OptC, String JSON_OptD,
                                String JSON_Answer){
        Integer id = JSON_ID;
        String Question_id = JSON_Question_id;
        String QP_Code =  JSON_QP_Code;
        String Question = JSON_Question;
        String OptA = JSON_OptA;
        String OptB = JSON_OptB;
        String OptC = JSON_OptC;
        String OptD = JSON_OptD;
        String Answer= JSON_Answer;


        db=openOrCreateDatabase("QuizCode", this.MODE_PRIVATE, null);
        String query = "INSERT OR IGNORE INTO question_table " +
                "(id ,Question_id ,QP_Code ,Question ,OptA ,OptB ,OptC ,OptD,Answer ) " +
                "VALUES('"+id+"', '"+Question_id+"', '"+QP_Code+"', '"+Question+"', '"+OptA+"', '"+OptB+"', '"+OptC+"', '"+OptD+"', '"+Answer+"');";
        db.execSQL(query);
        db.close();

    }


    public Integer checkIfPaperExists(String sQP_Code){

        Integer result = 0;
        String query = "SELECT DOWNLOADED FROM QuizCode WHERE QP_Code = ?";
        db=openOrCreateDatabase("QuizCode", MODE_PRIVATE, null);

        Cursor csr = db.rawQuery(query, new String[] {sQP_Code});

        if (csr != null ){

            if  (csr.moveToFirst()) {
                do {
                    result = csr.getInt(csr.getColumnIndex("DOWNLOADED"));
                }while (csr.moveToNext());
            }

        }
        csr.close();
        db.close();
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


            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                JSON_ID = json.optInt("id");
                JSON_QP_Code = json.optString("QP_Code");
                JSON_Question = json.optString("Question");
                JSON_Question_id = json.optString("Question_id");
                JSON_OptA = json.optString("OptA");
                JSON_OptB = json.optString("OptB");
                JSON_OptC = json.optString("OptC");
                JSON_OptD = json.optString("OptD");
                JSON_Answer = json.optString("Answer");
                insertIntoDB(JSON_ID,JSON_QP_Code,JSON_Question,JSON_Question_id,JSON_OptA,JSON_OptB,JSON_OptC,JSON_OptD,JSON_Answer);

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

    }


}
