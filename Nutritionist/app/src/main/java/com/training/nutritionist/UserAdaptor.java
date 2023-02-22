package com.training.nutritionist;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.training.nutritionist.ui.gallery.UserProfile;

import java.util.ArrayList;

public class UserAdaptor extends BaseAdapter {

    LayoutInflater inflator;
    ArrayList<User_DTO> arrayList;
    UserProfile userprofile;

    public UserAdaptor(UserProfile userprofile,ArrayList<User_DTO> arrayList){
        inflator = (LayoutInflater.from(userprofile.getContext()));
        this.arrayList = arrayList;
        this.userprofile=userprofile;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflator.inflate(R.layout.username_row,null);

        TextView textView = view.findViewById(R.id.tvFname);
        TextView textView2 = view.findViewById(R.id.tvLname);
        TextView textView3 = view.findViewById(R.id.tvEmail);

        User_DTO user_dto = arrayList.get(i);
        textView.setText(user_dto.fname);
        textView2.setText(user_dto.lname);
        textView3.setText(user_dto.email);

        /*
        if (i % 2 == 0) {
            view.setBackgroundColor(Color.parseColor("#FAAB9F"));
        } else {
            view.setBackgroundColor(Color.parseColor("#CEF1F1"));
        }

         */

        return view;
    }

}
