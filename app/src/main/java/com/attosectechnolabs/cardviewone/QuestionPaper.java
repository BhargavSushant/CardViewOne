package com.attosectechnolabs.cardviewone;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QuestionPaper extends AppCompatActivity {

    String sQP_Code;
    public SQLiteDatabase db;

    String id ,Question_id ,QP_Code ,Question ,OptA ,OptB ,OptC ,OptD,Answer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_paper);
        setTitle("ITI Question Paper");
        Intent intent = getIntent();

        sQP_Code = intent.getStringExtra("QP_Code1");
System.out.println("Question Paper "+sQP_Code);


        // create database for question_table if doesn't exists
createDatabase();

        // for QP_Code check if Downloaded column in Question paper code table is 0 or 1
        checkIfPaperExists(sQP_Code);

        // if 0 then download the question paper for QP_Code
        // if 1 then ignore download and move ahead

        // read questions for QP_Code from question table

        // generate card for questions.

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

    protected void insertIntoDB(Integer id1, String sQP_Code){
        Integer id = id1;
        String QP_Code = sQP_Code;
        db=openOrCreateDatabase("QuizCode", this.MODE_PRIVATE, null);
        String query = "INSERT OR IGNORE INTO question_table " +
                "(id ,Question_id ,QP_Code ,Question ,OptA ,OptB ,OptC ,OptD,Answer ) " +
                "VALUES('"+id+"', '"+Question_id+"', '"+QP_Code+"',, '"+Question+"', '"+OptA+"', '"+OptB+"', '"+OptC+"', '"+OptD+"', '"+Answer+"');";
        db.execSQL(query);
    }

    protected Integer checkIfPaperExists(String sQP_Code){

        Integer result = 0;
        String query = "SELECT DOWNLOADED FROM QuizCode WHERE QP_Code = "+sQP_Code;
        db=openOrCreateDatabase("QuizCode", this.MODE_PRIVATE, null);

        Cursor csr = db.rawQuery(query,null);

     //   rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});

        return result;

    }

}
