package barqsoft.footballscores.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by akhatri on 13/01/16.
 */
public class FootballScoresAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private FootballScoresAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new FootballScoresAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
