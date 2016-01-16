package barqsoft.footballscores.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.MainScreenFragment;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.Widget.FootballScoresWidget;

/**
 * Created by akhatri on 13/01/16.
 */
public class WidgetFetchService extends IntentService {

    public static final String LOG_TAG = "widget_intent_service";
    public static final int COL_HOME = 3;
    public static final int COL_AWAY = 4;
    public static final int COL_HOME_GOALS = 6;
    public static final int COL_AWAY_GOALS = 7;
    public static final int COL_DATE = 1;
    public static final int COL_LEAGUE = 5;
    public static final int COL_MATCHDAY = 9;
    public static final int COL_ID = 8;
    public static final int COL_MATCHTIME = 2;
    public double detail_match_id = 0;
    public WidgetFetchService(){ super(LOG_TAG);}

    @Override
    protected void onHandleIntent(Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                FootballScoresWidget.class));

        Uri footballScoresUri = DatabaseContract.BASE_CONTENT_URI;
        Cursor data = getContentResolver().query(footballScoresUri, null, null,
                null,null );

        if (data == null) {
            return;
        }
        if (!data.moveToFirst()) {
            data.close();
            return;
        }

        for(int appWidgetId : appWidgetIds){
            RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.widget_footballscores);

            Log.d("Home name", data.getString(COL_HOME));
            remoteViews.setTextViewText(R.id.home_name, data.getString(COL_HOME));
            remoteViews.setTextViewText(R.id.away_name, data.getString(COL_AWAY));
            remoteViews.setTextViewText(R.id.data_textview, data.getString(COL_MATCHTIME));
            remoteViews.setTextViewText(R.id.score_textview, Utilies.getScores(data.getInt(COL_HOME_GOALS), data.getInt(COL_AWAY_GOALS)));

            //remoteViews.setString(R.id.home_name,"setText","Ayush");

            remoteViews.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(
                    data.getString(COL_HOME)));
            remoteViews.setImageViewResource(R.id.away_crest,Utilies.getTeamCrestByTeamName(
                    data.getString(COL_AWAY)));


            Intent intentOpenApplication = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intentOpenApplication,0);
            remoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId,remoteViews);

        }
    }
}
