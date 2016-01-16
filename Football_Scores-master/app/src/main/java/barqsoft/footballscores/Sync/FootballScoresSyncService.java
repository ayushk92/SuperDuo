package barqsoft.footballscores.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by akhatri on 13/01/16.
 */
public class FootballScoresSyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static FootballScoresSyncAdapter footballScoresSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("FootbalSyncService", "onCreate - FootballScoresSyncService");
        synchronized (sSyncAdapterLock) {
            if (footballScoresSyncAdapter == null) {
                footballScoresSyncAdapter = new FootballScoresSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return footballScoresSyncAdapter.getSyncAdapterBinder();
    }
}
