package pec.com.tpopec.general;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Raghav on 09-05-2016.
 */
public class MySharedPreferences {

    private SharedPreferences sharedPreferences;

    // Constants will be defined here.
    public static final String SHARED_PREFERENCES = "TpoPrefs";
    public static final String SHARED_PREFERENCES_SID = "sid";
    public static final String SHARED_PREFERENCES_PASSWORD = "password";
    public static final String SHARED_PREFERENCES_BRANCH = "branch";
    public static final String SHARED_PREFERENCES_STUDENT_NAME = "student_name";
    public static final String SHARED_PREFERENCES_STUDENT_PROGRAMME = "student_programme";
    public static final String SHARED_PREFERENCES_GCM_TOKEN = "gcm_token";

    public MySharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public String getSid() {
        return sharedPreferences.getString(SHARED_PREFERENCES_SID, null);
    }

    public String getPassword() {
        return sharedPreferences.getString(SHARED_PREFERENCES_PASSWORD, null);
    }

    public String getBranch() {
        return sharedPreferences.getString(SHARED_PREFERENCES_BRANCH, null);
    }

    public String getGcmToken(){
        return sharedPreferences.getString(SHARED_PREFERENCES_GCM_TOKEN, null);
    }

    public boolean setSid(String sid) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_SID, sid);
        return editor.commit();
    }

    public boolean setPassword(String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_PASSWORD, password);
        return editor.commit();
    }

    public boolean setBranch(String branch) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_BRANCH, branch);
        return editor.commit();
    }

    public boolean setGcmToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_GCM_TOKEN, token);
        return editor.commit();
    }

    public boolean setStudentName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_STUDENT_NAME, name);
        return editor.commit();
    }

    public String getStudentName() {
        return sharedPreferences.getString(SHARED_PREFERENCES_STUDENT_NAME, null);
    }

    public boolean setStudentProgramme(String prog) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_STUDENT_PROGRAMME, prog);
        return editor.commit();
    }

    public String getStudentProgramme() {
        return sharedPreferences.getString(SHARED_PREFERENCES_STUDENT_PROGRAMME, null);
    }
}
