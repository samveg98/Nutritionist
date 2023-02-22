package com.training.nutritionist.ApiHandler;

import com.google.gson.Gson;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class Apihandler {

    private static final String BASE_URL ="http://192.168.0.11/db_drcalory/";

    // private static final String BASE_URL ="http://desireinfotech.biz/drCalory_apis/";
    private static Webservice apiService;

    public static Webservice getApiService() {
        if (apiService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(BASE_URL)
                    .setConverter(new GsonConverter(new Gson()))
                    .build();
            apiService = restAdapter.create(Webservice.class);
            return apiService;
        }
        else
        {
            return apiService;
        }
    }
}
