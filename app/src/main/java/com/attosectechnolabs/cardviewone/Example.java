package com.attosectechnolabs.cardviewone;

/**
 * Created by dev on 26-Aug-16.
 */

public class Example{

    String Question_id ,Question ,OptA ,OptB ,OptC ,OptD,Answer;

    String QP_Code;
    Integer id;

    public Example() {

    }

    public Example(int id, String QP_Code, String Question_id, String Question,String OptA,
                   String OptB, String OptC,String OptD,String Answer) {
        this.id = id;
        this.QP_Code = QP_Code;
        this.Question_id = Question_id;
        this.Question = Question;
        this.OptA = OptA;
        this.OptB = OptB;
        this.OptC = OptC;
        this.OptD = OptD;
        this.Answer = Answer;
            }
}