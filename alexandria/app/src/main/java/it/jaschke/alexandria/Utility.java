package it.jaschke.alexandria;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import it.jaschke.alexandria.services.BookService;

/**
 * Created by akhatri on 17/01/16.
 */
public class Utility {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_OK, STATUS_SERVER_DOWN, STATUS_SERVER_INVALID,  STATUS_UNKNOWN})
    public @interface AppStatus {}

    public static final int STATUS_OK = 0;
    public static final int STATUS_SERVER_DOWN = 1;
    public static final int
            STATUS_SERVER_INVALID = 2;
    public static final int
            STATUS_UNKNOWN = 3;

    public static void setApptatus(Context c, @AppStatus int appStatus){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt(c.getString(R.string.sharedpreference_appstatus_key), appStatus);
        spe.commit();
    }

    @SuppressWarnings("ResourceType")
    static public @AppStatus
    int getAppStatus(Context c){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getInt(c.getString(R.string.sharedpreference_appstatus_key), STATUS_UNKNOWN);
    }

    public static boolean IsNetworkAvailable(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static int getEmptyViewMessage(Context context){

        int message = R.string.addbook_unknown_text;
        int appStatus = getAppStatus(context);
        switch (appStatus){
            case STATUS_SERVER_DOWN:
                message = R.string.addbook_serverdown_text;
                break;
            case STATUS_SERVER_INVALID:
                message = R.string.addbook_serverinvalid_text;
                break;
        }

        return message;
    }
}
