package com.example.android.mb24an.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Mark on 2017/8/3.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String  REG_TOKEN  = "REG_TOKEN";

    @Override
    public void onTokenRefresh(){
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN,recent_token);
    }
}
