package com.attosectechnolabs.cardviewone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class forum extends AppCompatActivity
{

    String thread_text, User = "Test";
    Context ctx;
    EditText new_thread_text;
    TextView resulttemp;
    Button submit_thread, cancel_thread,create_new_thread;
    LinearLayout new_thread_layout_part;
    private static final String REGISTER_URL = "http://attosectechnolabs.com/Projects/eduapp/thread.php";
    public static final String KEY_THREAD = "thread_text";
    public static final String KEY_USERNAME = "User";


    //==========================================

    List<GetDataAdapter> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;
    ProgressBar progressBar;

    String GET_JSON_DATA_HTTP_URL = "http://attosectechnolabs.com/Projects/eduapp/getThreadService.php";

    String JSON_ID="id";
    String JSON_THREAD_ID = "ThreadID";
    String JSON_THREAD_TEXT = "thread_text" ;
    String JSON_USER_NAME = "User";
    String JSON_FLAG = "Flag";
    String JSON_LIKES = "Likes";

    Button button;

    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;

    // ======================================


    public forum() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setTitle("ITI Forum");
        setContentView(R.layout.forum);

                submit_thread = (Button) findViewById(R.id.submit_thread);
                cancel_thread = (Button) findViewById(R.id.cancel_thread);
                create_new_thread = (Button)findViewById(R.id.create_new_thread);
                new_thread_text = (EditText) findViewById(R.id.new_thread_text);
                resulttemp = (TextView) findViewById(R.id.resulttemp);
                new_thread_layout_part = (LinearLayout) findViewById(R.id.new_thread_layout_part);


        //======================================
        GetDataAdapter1 = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        //======================================

        // set new_thread_layout_part VISIBLE.GONE
            new_thread_layout_part.setVisibility(View.GONE);

        // get threads from database
            progressBar.setVisibility(View.VISIBLE);
            JSON_DATA_WEB_CALL();

        submit_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                thread_text = new_thread_text.getText().toString();
                if(thread_text.isEmpty()){new_thread_text.setError("This field can not be empty ...");}
                else {
                    insertThreadText(thread_text);
                    // after submitting thread invisible the new_thread_layout_part
                    new_thread_text.setText("");
                    new_thread_layout_part.setVisibility(View.GONE);

                }


            }// end view
             });//end submit_thread.setOnclickListener

        cancel_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_thread_layout_part.setVisibility(View.GONE);
            }
        });

        create_new_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on clicking new_thread_layout_part we need new thread layout to be visible
                new_thread_layout_part.setVisibility(View.VISIBLE);
        }
        });// end new_thread_layout_part



            }

    /* =========================== functions =================================*/

    private void insertThreadText(String thread_text) {

        //resulttemp.setText(thread_text);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(forum.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(forum.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_THREAD, forum.this.thread_text);
                params.put(KEY_USERNAME, User);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(forum.this);
        requestQueue.add(stringRequest);
    }



    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.GONE);

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
                GetDataAdapter2.setThreadID(json.getString(JSON_THREAD_ID));
                GetDataAdapter2.setThread_text(json.getString(JSON_THREAD_TEXT));
                GetDataAdapter2.setUser(json.getString(JSON_USER_NAME));
                GetDataAdapter2.setFlag(json.getInt(JSON_FLAG));
                GetDataAdapter2.setLikes(json.getInt(JSON_LIKES));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }


}// end class
