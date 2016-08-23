package com.attosectechnolabs.cardviewone;

public class GetDataAdapter {

    int Id;
    String ThreadID;
    String thread_text;
    String User;
    Integer Flag;
    Integer Likes;
    String ThreadIDPost;
    String PostTextTV, UserNameTV, ThreadTextTV;
    Integer FlagIV,   LikeIV;

    String QP_Code;
    Integer QP_Id;


    public String getThreadID(){
        return ThreadID;
    }
    public String getThread_text(){
        return thread_text;
    }

    public String getUser(){
        return User;
    }

    public Integer getFlag(){
        return Flag;
    }

    public String getThreadIDPost(){
        return ThreadIDPost;
    }
    public int getId() {        return Id;    }

    // RVAdapter ka saman

    public String getUserNameTV(){
        return UserNameTV;
    }
    public String getThreadTextTV(){
        return ThreadTextTV;
    }
    public String getPostTextTV(){
        return PostTextTV;
    }
    public Integer getLikeIV(){
        return LikeIV;
    }
    public Integer getFlagIV(){
        return FlagIV;
    }




    public void setThreadID( String ThreadID ){
        this.ThreadID=ThreadID;
    }

    public void setThread_text( String thread_text ){
        this.thread_text=thread_text ;
    }

    public void setUser( String User){
        this.User=User ;
    }

    public void setFlag( Integer Flag){
        this.Flag= Flag;
    }

    public void setLikes( Integer Likes){
        this.Likes=Likes ;
    }

    public void setId(Integer Id)
    {
        this.Id=Id;
    }

    public void setUserNameTV( String UserNameTV){        	this.UserNameTV	=UserNameTV;    }
    public void setThreadTextTV(String ThreadTextTV){        	this.ThreadTextTV	=ThreadTextTV;    }
    public void setPostTextTV(String PostTextTV){        	this.PostTextTV	=PostTextTV;    }
    public void setLikeIV(Integer LikeIV){        	this.LikeIV	=LikeIV;    }
    public void setFlagIV(Integer FlagIV){        	this.FlagIV	=FlagIV;    }


    // for QuizActivity

    public Integer getQP_Id(){return QP_Id;}
    public void setQP_Id(Integer QP_Id){this.QP_Id = QP_Id;}

    public String getQP_Code(){return QP_Code;}
    public void setQP_Code(String QP_Code){this.QP_Code = QP_Code;}

}
