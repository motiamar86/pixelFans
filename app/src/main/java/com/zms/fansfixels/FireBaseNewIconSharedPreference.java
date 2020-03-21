package com.zms.fansfixels;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class FireBaseNewIconSharedPreference {

    SharedPreferences pref;
    private static FireBaseNewIconSharedPreference instance = null;

    private FireBaseNewIconSharedPreference() {
       pref = ApplicationClass.getAppContext().getSharedPreferences(FireBaseConstance.SHARED_NAME, 0);
    }

    public synchronized static FireBaseNewIconSharedPreference getInstance() {
        if (instance == null) {
            instance = new FireBaseNewIconSharedPreference();
        }
        return instance;
    }

    public void saveNewLIst(String newList){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(FireBaseConstance.EVENT_FLASH_DETAILS, newList);
        editor.apply();
    }

    public JSONObject getFlashDetails(){
        try {
            return new JSONObject(pref.getString(FireBaseConstance.EVENT_FLASH_DETAILS, null));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStartEvent(){
        JSONObject time = getFlashDetails();

        try {
            return time.getString(FireBaseConstance.START_EVENT_TIME);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getEventImageX(){
        JSONObject time = getFlashDetails();
        try {
            time = time.getJSONObject(FireBaseConstance.IMAGE_SIZE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            return time.getInt(FireBaseConstance.IMAGE_X);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
    public int getEventImageY(){
        JSONObject time = getFlashDetails();
        try {
            time = time.getJSONObject(FireBaseConstance.IMAGE_SIZE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            return time.getInt(FireBaseConstance.IMAGE_Y);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean isNeedToShowNewIcon(int index){
        String newIconList = getStartEvent();
        String [] iconArray;
        if(newIconList != null){
            iconArray = newIconList.split(",");
            if(iconArray[index].equals("1")){
                return true;
            }
        }
        return false;
    }

}
