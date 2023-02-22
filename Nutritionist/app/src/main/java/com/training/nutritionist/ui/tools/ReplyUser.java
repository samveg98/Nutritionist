package com.training.nutritionist.ui.tools;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.training.nutritionist.ApiHandler.Apihandler;
import com.training.nutritionist.Model.getQuestionModel;
import com.training.nutritionist.Model.getUsernameModel;
import com.training.nutritionist.Model.sendReplyModel;
import com.training.nutritionist.R;

import org.apache.commons.lang3.StringUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class ReplyUser extends Fragment {

    Button getNewQues,btnsend;
    TextView tvmail,tvquestion,tvreply;
    private String usermail,question;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_replyuser, container, false);

        getNewQues = root.findViewById(R.id.newques);
        btnsend = root.findViewById(R.id.btnsend);

        tvmail = root.findViewById(R.id.tvmail);
        tvquestion = root.findViewById(R.id.tvquestion);
        tvreply = root.findViewById(R.id.tvreply);


        //first, user will click on to get the new question from the user if there is any available in the database
        //if there is any, then it will be displayed to the nutriotionist and then he can answer to that .
        getNewQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Apihandler.getApiService().getQuestion(new Callback<getQuestionModel>() {
                    @Override
                    public void success(getQuestionModel getquestionmodel, Response response)
                    {
                        String value = new String(((TypedByteArray) response.getBody()).getBytes());
                        usermail = StringUtils.substringBetween(value,"email\":\"" , "\",");
                        question = StringUtils.substringBetween(value,"question\":\"" , "\"}");
                        Toast.makeText(getActivity(),"Updated", Toast.LENGTH_SHORT).show();

                        tvmail.setText(usermail);
                        tvquestion.setText(question);
                        tvreply.setText("");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        String er = error.toString();
                        Log.d("<>", er);
                        Toast.makeText(getActivity(), "failed:" + er, Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });




        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usermail = tvmail.getText().toString();
                String reply = tvreply.getText().toString();

                Log.d("<>", usermail);
                Log.d("<>", reply);

                Apihandler.getApiService().sendReply(usermail, reply ,new Callback<sendReplyModel>() {
                    @Override
                    public void success(sendReplyModel sendreplymodel, Response response)
                    {

                        Toast.makeText(getActivity(),"Reply Sent", Toast.LENGTH_SHORT).show();
                        tvmail.setText("");
                        tvquestion.setText("");

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        String er = error.toString();
                        Log.d("<>", er);
                        Toast.makeText(getActivity(), "failed:" + er, Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });


        return root;
    }
}