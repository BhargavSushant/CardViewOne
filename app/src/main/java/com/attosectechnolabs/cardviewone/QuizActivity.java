package com.attosectechnolabs.cardviewone;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
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

public class QuizActivity extends AppCompatActivity {

    List<GetDataAdapter> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;

    private static final String GET_JSON_DATA_HTTP_URL
            = "http://attosectechnolabs.com/Projects/eduapp/getAllQuiz.php";
    public static final String KEY_QUIZ_CODE = "QP_Code";
    public static final String KEY_USERNAME = "User";

    String JSON_QPCODE = "QP_Code" ;

    public String JSON_ID = "Id";

    ProgressBar progressBar;
    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;

    public String sQP_Code;
    public Integer id1;
    public Integer DOWNLOADED1;
    public SQLiteDatabase db;

    public LinearLayout QP_Code_layout;

    //===========================================================================
    String GET_JSON_DATA_HTTP_URL2 = "http://attosectechnolabs.com/Projects/eduapp/getAllQuestions.php";

    public Integer JSON_Id;
    public String JSON_Question_id="Question_id";
    public String JSON_QP_Code = "QP_Code";
    public String JSON_Question  = "Question";
    public String JSON_OptA ="OptA";
    public String JSON_OptB = "OptB";
    public String JSON_OptC = "OptC";
    public String JSON_OptD = "OptD";
    public String JSON_Answer = "Answer";

    RequestQueue requestQueue2 ;

    //=============================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setTitle("ITI Quiz Arena");

        // create database if not exist
        createDatabase();

        //======================================
        GetDataAdapter1 = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView3);

        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        //======================================

        QP_Code_layout = (LinearLayout) findViewById(R.id.QP_Code_layout);


        // get question paper codes from database
            progressBar = (ProgressBar) findViewById(R.id.progressBar3);
            progressBar.setVisibility(View.VISIBLE);
            JSON_DATA_WEB_CALL();

    }

    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                      //  progressBar.setVisibility(View.GONE);

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
        GetDataAdapter GetDataAdapter2;
        for(int i = 0; i<array.length(); i++) {

            GetDataAdapter2 = new GetDataAdapter();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                sQP_Code = json.optString("QP_Code");
                id1 = json.optInt("id");
                DOWNLOADED1 = json.optInt("DOWNLOADED");
                GetDataAdapter2.setQP_Id(id1);
                GetDataAdapter2.setQP_Code(sQP_Code);
                GetDataAdapter2.setDOWNLOADED(DOWNLOADED1);

                // call method for handling sqlite database
                insertIntoDB(id1,sQP_Code,DOWNLOADED1);

            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);

        }

        recyclerViewadapter = new RVAdapterQP(GetDataAdapter1, this);
        recyclerView.setAdapter(recyclerViewadapter);

    }

    protected void createDatabase(){
        db=openOrCreateDatabase("QuizCode", this.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS QuizCode(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, QP_Code VARCHAR UNIQUE, DOWNLOADED INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS question_table " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "Question_id VARCHAR UNIQUE , QP_Code VARCHAR," +
                "Question VARCHAR , OptA VARCHAR," +
                "OptB VARCHAR , OptC VARCHAR," +
                "OptD VARCHAR , Answer VARCHAR" +
                ");");


         }

    protected void insertIntoDB(Integer id1, String sQP_Code, Integer DOWNLOADED1){
        Integer id = id1;
        String QP_Code = sQP_Code;

        String query = "INSERT OR IGNORE INTO QuizCode (id,QP_Code,DOWNLOADED) VALUES('"+id+"', '"+QP_Code+"','"+DOWNLOADED1+"');";
        db.execSQL(query);

        downloadPaperAndUpdateSQLiteDB();  // download paper

    }


//=========================================================================================
    private void downloadPaperAndUpdateSQLiteDB() {

        JSON_DATA_WEB_CALL2();
    }

    public void JSON_DATA_WEB_CALL2(){

        String GET_JSON_DATA_HTTP_URL3 = GET_JSON_DATA_HTTP_URL2+"?QP_Code="+sQP_Code;
        System.out.println(GET_JSON_DATA_HTTP_URL3);
        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL3,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //   progressBar.setVisibility(View.GONE);
                        JSON_PARSE_DATA_AFTER_WEBCALL2(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL2(JSONArray array){

        for(int i = 0; i<array.length(); i++) {


            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                JSON_Id = json.optInt("id");
                JSON_QP_Code = json.optString("QP_Code");
                JSON_Question = json.optString("Question");
                JSON_Question_id = json.optString("Question_id");
                JSON_OptA = json.optString("OptA");
                JSON_OptB = json.optString("OptB");
                JSON_OptC = json.optString("OptC");
                JSON_OptD = json.optString("OptD");
                JSON_Answer = json.optString("Answer");
                insertIntoDB(JSON_Id,JSON_QP_Code,JSON_Question,JSON_Question_id,JSON_OptA,JSON_OptB,JSON_OptC,JSON_OptD,JSON_Answer);

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

    }

    protected void insertIntoDB(Integer JSON_Id,
                                String JSON_QP_Code,
                                String JSON_Question,
                                String JSON_Question_id,
                                String JSON_OptA, String JSON_OptB, String JSON_OptC, String JSON_OptD,
                                String JSON_Answer){
        Integer id = JSON_Id;
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
}
