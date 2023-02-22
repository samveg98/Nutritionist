package com.training.nutritionist.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.training.nutritionist.ApiHandler.Apihandler;
import com.training.nutritionist.Model.getUsernameModel;
import com.training.nutritionist.R;
import com.training.nutritionist.UserAdaptor;
import com.training.nutritionist.UserDetail;
import com.training.nutritionist.User_DTO;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class UserProfile extends Fragment {

    private String value;
    private String[] uemail,ufname,ulname;
    private List<String> Useremail,Userfname,Userlname;

    ListView listView;
    ArrayList<User_DTO> arrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_userprofile, container, false);

        listView = root.findViewById(R.id.listViewUsers);

        arrayList = new ArrayList<User_DTO>();


        Apihandler.getApiService().getUsername(new Callback<getUsernameModel>() {
            @Override
            public void success(getUsernameModel getusernamemodel, Response response)
            {
                value = new String(((TypedByteArray) response.getBody()).getBytes());

                //get the user name and email info from the getUser api call and storing them into array list
                Useremail = new ArrayList<String>();
                uemail = StringUtils.substringsBetween(value, "email\":\"", "\",");
                Useremail = Arrays.asList(uemail);

                Userfname = new ArrayList<String>();
                ufname = StringUtils.substringsBetween(value, "fname\":\"", "\",");
                Userfname = Arrays.asList(ufname);

                Userlname= new ArrayList<String>();
                ulname = StringUtils.substringsBetween(value, "lname\":\"", "\"}");
                Userlname = Arrays.asList(ulname);

                int i =0;
                while(i<Useremail.size())
                {
                    User_DTO ob = new User_DTO();
                    ob.fname = ufname[i];
                    ob.lname = ulname[i];
                    ob.email = uemail[i];

                    arrayList.add(ob);

                    i++;
                }

                UserAdaptor userAdaptor = new UserAdaptor(UserProfile.this,arrayList);

                listView.setAdapter(userAdaptor);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        int i = (int) listView.getItemAtPosition(position);
                        String mail = uemail[i];
                        String fname = ufname[i];
                        String lname = ulname[i];

                        Intent intent = new Intent(getContext(), UserDetail.class);

                        intent.putExtra("selectedEmail", mail);
                        intent.putExtra("selectedFname", fname);
                        intent.putExtra("selectedLname", lname);

                        startActivity(intent);

                    }});


            }

            @Override
            public void failure(RetrofitError error) {
                String er = error.toString();
                Log.d("<>", er);
                Toast.makeText(getActivity(), "failed:" + er, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}