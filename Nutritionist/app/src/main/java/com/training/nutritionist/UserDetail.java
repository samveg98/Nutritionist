package com.training.nutritionist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.training.nutritionist.ApiHandler.Apihandler;
import com.training.nutritionist.Model.getUsernameModel;
import com.training.nutritionist.Model.userDetailModel;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class UserDetail extends AppCompatActivity {

    TextView tvName,tvmail,tvbday,tvgender,tvheight,tvweight,tvlifestyle,tvmedical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        tvName = findViewById(R.id.tvName);
        tvmail = findViewById(R.id.tvmail);

        tvbday = findViewById(R.id.tvbday);
        tvgender = findViewById(R.id.tvgender);
        tvheight = findViewById(R.id.tvheight);
        tvweight = findViewById(R.id.tvweight);
        tvlifestyle = findViewById(R.id.tvlifestyle);
        tvmedical = findViewById(R.id.tvmedical);

        Intent intent = getIntent();
        String email = intent.getStringExtra("selectedEmail");
        String fname = intent.getStringExtra("selectedFname");
        String lname = intent.getStringExtra("selectedLname");

        tvName.setText(fname + " " + lname);
        tvmail.setText(email);

        Apihandler.getApiService().userDetail(email, new Callback<userDetailModel>() {
            @Override
            public void success(userDetailModel userdetailmodel, Response response) {
                String value = new String(((TypedByteArray) response.getBody()).getBytes());

                if(value.contains("Profile Not Set"))
                {
                    Toast.makeText(getApplicationContext(), "Profile Not Set", Toast.LENGTH_SHORT).show();
                }
                else if(value.contains("Profile Not Updated"))
                {
                    Toast.makeText(getApplicationContext(), "Profile Not Updated", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "User Profile", Toast.LENGTH_SHORT).show();

                    String bday = StringUtils.substringBetween(value, "birthday\":\"", "\",");
                    String bday1 = bday.replace("\\","");
                    tvbday.setText(bday1);

                    String gender = StringUtils.substringBetween(value, "gender\":\"", "\",");
                    tvgender.setText(gender);

                    String height = StringUtils.substringBetween(value, "height\":\"", "\",");
                    tvheight.setText(height);

                    String weight = StringUtils.substringBetween(value, "weight\":\"", "\",");
                    tvweight.setText(weight);

                    String lifestyle = StringUtils.substringBetween(value, "lifestyle\":\"", "\",");
                    tvlifestyle.setText(lifestyle);

                    String med_cond = StringUtils.substringBetween(value, "medical_condition\":\"", "\"}" );
                    tvmedical.setText(med_cond);
                }


            }

            @Override
            public void failure(RetrofitError error) {
                String er = error.toString();
                Toast.makeText(getApplicationContext(), "failed:" + er, Toast.LENGTH_SHORT).show();
            }

        });
    }
}
