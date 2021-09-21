package com.example.prettybakeduser;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.prettybakeduser.ApiHelper.JsonField;

public class UserSessionManager {
    private static final String MY_PREF="user_pref";
    private static final String IS_LOGIN="is_login";

    Context mcontext;
    SharedPreferences msharedPreferences;

    private final SharedPreferences.Editor mEditor;

    public UserSessionManager(Context mcontext) {
        this.mcontext = mcontext;
        msharedPreferences=mcontext.getSharedPreferences(MY_PREF,Context.MODE_PRIVATE);
        mEditor=msharedPreferences.edit();
    }
    public void setLoginStatus()
    {
        mEditor.putBoolean(IS_LOGIN,true).commit();
    }
    public boolean getLoginStatus(){
        return msharedPreferences.getBoolean(IS_LOGIN,false);
    }

    public void setUserDetails(String strUserId,String strUserName,String strUserEmail,String strUserGender,String strUserMobile,String strUserAddress){
        mEditor.putString(JsonField.USERID,strUserId);
        mEditor.putString(JsonField.USERNAME,strUserName);
        mEditor.putString(JsonField.USEREMAIl,strUserEmail);
        mEditor.putString(JsonField.USERGENDER,strUserGender);
        mEditor.putString(JsonField.USERAddress,strUserAddress);
        mEditor.putString(JsonField.USERMOBILE,strUserMobile);
        mEditor.commit();
    }
    public void logout(){
        mEditor.remove(JsonField.USERID);
        mEditor.remove(JsonField.USERNAME);
        mEditor.remove(JsonField.USEREMAIl);
        mEditor.remove(JsonField.USERGENDER);
        mEditor.remove(JsonField.USERMOBILE);
        mEditor.remove(JsonField.USERAddress);
        mEditor.remove(IS_LOGIN);
        mEditor.commit();
    }
    public String getUSERID(){
        return msharedPreferences.getString(JsonField.USERID,"");
    }
    public String getUSERNAME(){ return msharedPreferences.getString(JsonField.USERNAME,"");}
    public String getUSEREMAIL(){
        return msharedPreferences.getString(JsonField.USEREMAIl,"");
    }
    public String getUSERADDRESS(){ return msharedPreferences.getString(JsonField.USERAddress,"");}
    public String getUSERMOBILENO(){ return msharedPreferences.getString(JsonField.USERMOBILE,"");}

}
