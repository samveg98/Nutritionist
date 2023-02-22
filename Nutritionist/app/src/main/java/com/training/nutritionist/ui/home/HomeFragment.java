package com.training.nutritionist.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.training.nutritionist.ApiHandler.Apihandler;
import com.training.nutritionist.Model.getUsernameModel;
import com.training.nutritionist.Model.setDailyModel;
import com.training.nutritionist.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class HomeFragment extends Fragment {

    private EditText etbreakfast, etlunch, etsnack, etdinner ;
    private Spinner spUser;
    private String breakfast,lunch,snack,dinner;
    private Button set;

    private String value, uemail;
    private String[] uname;
    private List<String> Username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_setdietplan, container, false);

        etbreakfast = root.findViewById(R.id.etBreakfast);
        etlunch = root.findViewById(R.id.etLunch);
        etsnack = root.findViewById(R.id.etSnack);
        etdinner = root.findViewById(R.id.etDinner);

        spUser = root.findViewById(R.id.spUser);
        set = root.findViewById(R.id.btnSet);


        Apihandler.getApiService().getUsername(new Callback<getUsernameModel>() {
            @Override
            public void success(getUsernameModel getusernamemodel, Response response)
            {
                //first we will fetch all the user, so that Nutritionist can see ..
                //the dropdown containing all the user names and can select and set the diet plan for ech user
                value = new String(((TypedByteArray) response.getBody()).getBytes());

                Username = new ArrayList<String>();

                uname = StringUtils.substringsBetween(value, "email\":\"", "\",");

                Username = Arrays.asList(uname);

                final ArrayAdapter<String> UserAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Username);

                UserAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spUser.setAdapter(UserAdapter);

            }

            @Override
            public void failure(RetrofitError error) {
                String er = error.toString();
                Log.d("<>", er);
                Toast.makeText(getActivity(), "failed:" + er, Toast.LENGTH_SHORT).show();
            }

        });




        spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                uemail = spUser.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                breakfast = etbreakfast.getText().toString();
                lunch = etlunch.getText().toString();
                snack = etsnack.getText().toString();
                dinner = etdinner.getText().toString();

                ConnectivityManager conMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

                if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
                    Toast.makeText(getContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
                }

                //Nutritionist will add the breakfast, lunch and dinner for each user
                else if (breakfast.equals("") || lunch.equals("") || snack.equals("") || dinner.equals("")){

                    Toast.makeText(getActivity(),"Field Can't be BLANK..!!",Toast.LENGTH_LONG).show();
                }
                else{

                    //when the api call is successful, it will display updated successfully
                    Apihandler.getApiService().setDaily(uemail,breakfast, lunch, snack, dinner, new Callback<setDailyModel>() {
                        @Override
                        public void success(setDailyModel setdailymodel, Response response) {

                            Toast.makeText(getActivity(), "Diet Updated Successfully", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            String er = error.toString();
                            Log.d("<>", er);
                            Toast.makeText(getActivity(), "failed:" + er, Toast.LENGTH_SHORT).show();
                        }


                    });
                }
            }
        });



        return root;
    }
}