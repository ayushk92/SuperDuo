package barqsoft.footballscores.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import barqsoft.footballscores.Sync.FootballScoresSyncAdapter;
import barqsoft.footballscores.service.WidgetFetchService;


/**
 * Created by akhatri on 13/01/16.
 */
public class FootballScoresWidget extends AppWidgetProvider{


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Intent service_start = new Intent(context, WidgetFetchService.class);
        context.startService(service_start);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(FootballScoresSyncAdapter.ACTION_DATA_UPDATED.equals(intent.getAction())){
             context.startService(new Intent(context,WidgetFetchService.class));
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Intent service_start = new Intent(context, WidgetFetchService.class);
        context.startService(service_start);
    }
}
