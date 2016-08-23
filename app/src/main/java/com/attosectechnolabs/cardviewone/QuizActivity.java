package com.attosectechnolabs.cardviewone;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    public SQLiteDatabase db;

    public LinearLayout QP_Code_layout;


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
                sQP_Code = json.optString("QP_Code").toString();
                id1 = json.optInt("id");

                GetDataAdapter2.setQP_Id(id1);
                GetDataAdapter2.setQP_Code(sQP_Code);

                // call method for handling sqlite database
                insertIntoDB(id1,sQP_Code);

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
    }

    protected void insertIntoDB(Integer id1, String sQP_Code){
        Integer id = id1;
        String QP_Code = sQP_Code;

        String query = "INSERT OR IGNORE INTO QuizCode (id,QP_Code) VALUES('"+id+"', '"+QP_Code+"');";
        db.execSQL(query);
    }

}
