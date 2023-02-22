package com.training.nutritionist.ApiHandler;

import com.training.nutritionist.Model.getQuestionModel;
import com.training.nutritionist.Model.getUsernameModel;
import com.training.nutritionist.Model.sendReplyModel;
import com.training.nutritionist.Model.setDailyModel;
import com.training.nutritionist.Model.userDetailModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface Webservice {

    @POST("/setDaily.php")
    public void setDaily(@Query("uemail") String uemail,
                       @Query("breakfast") String breakfast,
                       @Query("lunch") String lunch,
                       @Query("snack") String snack,
                       @Query("dinner") String dinner,
                       Callback<setDailyModel> callback);

    @POST("/getUserName.php")
    public void getUsername(Callback<getUsernameModel> callback);

    @POST("/userDetail.php")
    public void userDetail(@Query("mail") String mail,
                         Callback<userDetailModel> callback);

    @POST("/getQuestion.php")
    public void getQuestion(Callback<getQuestionModel> callback);

    @POST("/sendReply.php")
    public void sendReply(@Query("mail") String mail,
                          @Query("reply") String reply,
                           Callback<sendReplyModel> callback);

}
