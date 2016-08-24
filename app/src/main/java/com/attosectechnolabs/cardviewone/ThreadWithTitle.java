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

/**
* Created by dev on 16-Aug-16.
*/
public class ThreadWithTitle extends AppCompatActivity {


String post_text, User = "TestUser", ThreadID="T16081437",ThreadText;
Context ctx;
EditText ETPost;
TextView thread_topic,UserNamePost,PostByUser,DateOfPost,thread_id;
private static final String REGISTER_URL = "http://attosectechnolabs.com/Projects/eduapp/insertPost.php";
public static final String KEY_POST = "Post";
public static final String KEY_USERNAME = "User";
public static final String KEY_THREADID = "ThreadID";


//==========================================

List<GetDataAdapter> GetDataAdapter1;
RecyclerView recyclerView2;
RecyclerView.LayoutManager recyclerViewlayoutManager;
RecyclerView.Adapter recyclerViewadapter;
ProgressBar progressBar;

String GET_JSON_DATA_HTTP_URL = "http://attosectechnolabs.com/Projects/eduapp/getAllPosts2.php";

String JSON_ID="id";
String JSON_POST_ID = "PostID";
String JSON_POST_TEXT = "Post" ;
String JSON_USER_NAME = "User";
String JSON_FLAG = "FlagPost";
String JSON_LIKES = "LikesPost";



Button submit_post_btn, cancel_post_btn;

JsonArrayRequest jsonArrayRequest ;
RequestQueue requestQueue ;

// ======================================


public ThreadWithTitle(){}


@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
    setTitle("ITI Forum");

    setContentView(R.layout.thread_with_title);

/*this code to get ThreadID and ThreadText send from putextra method*/
Intent intent = getIntent();
ThreadID=intent.getStringExtra("ThreadID1");
ThreadText=intent.getStringExtra("ThreadText1");

thread_topic = (TextView) findViewById(R.id.ThreadTopicPost);
thread_id = (TextView) findViewById(R.id.ThreadIDPost);
UserNamePost = (TextView) findViewById(R.id.UserNamePost);
PostByUser   = (TextView) findViewById(R.id.PostByUser);
DateOfPost   = (TextView) findViewById(R.id.DateOfPost);
ETPost = (EditText) findViewById(R.id.ETPost);
submit_post_btn = (Button) findViewById(R.id.submit_post);
cancel_post_btn = (Button) findViewById(R.id.cancel_post);

/*this code to SET ThreadID and ThreadText send from putextra method*/

thread_topic.setText(ThreadText);
thread_id.setText(ThreadID);


//======================================
GetDataAdapter1 = new ArrayList<>();
recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
progressBar = (ProgressBar) findViewById(R.id.progressBar1);
recyclerView2.setHasFixedSize(true);
recyclerViewlayoutManager = new LinearLayoutManager(this);
recyclerView2.setLayoutManager(recyclerViewlayoutManager);
//======================================

//========================= get data to send ============================//



//    progressBar.setVisibility(View.VISIBLE);
JSON_DATA_WEB_CALL();

submit_post_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        post_text = ETPost.getText().toString();
        if(post_text.isEmpty()){ETPost.setError("This feild can not be empty ...");}
        else {insertPost();}
        ETPost.setText("");

    }// end view
});//end submit_thread.setOnclickListener

cancel_post_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
    }
});

}


private void insertPost() {

final String thread_text = ETPost.getText().toString();

StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ThreadWithTitle.this, response, Toast.LENGTH_LONG).show();
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ThreadWithTitle.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(KEY_POST, post_text);
        params.put(KEY_USERNAME, User);
        params.put(KEY_THREADID, ThreadID);
        return params;
    }
};

RequestQueue requestQueue = Volley.newRequestQueue(ThreadWithTitle.this);
requestQueue.add(stringRequest);
}

public void JSON_DATA_WEB_CALL(){

String GET_JSON_DATA_HTTP_URL2 = GET_JSON_DATA_HTTP_URL+"?ThreadID="+ThreadID;

    jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL2,

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

       // GetDataAdapter2.setId(json.getInt(JSON_ID));
       // GetDataAdapter2.setThreadID(json.getString(JSON_POST_ID));
        GetDataAdapter2.setPostTextTV(json.getString(JSON_POST_TEXT));
        GetDataAdapter2.setUserNameTV(json.getString(JSON_USER_NAME));
        GetDataAdapter2.setFlagIV(json.getInt(JSON_FLAG));
        GetDataAdapter2.setLikeIV(json.getInt(JSON_LIKES));


    } catch (JSONException e) {

        e.printStackTrace();
    }
    GetDataAdapter1.add(GetDataAdapter2);
}

recyclerViewadapter = new RVAdapterPosts(GetDataAdapter1, this);

recyclerView2.setAdapter(recyclerViewadapter);
}


}
