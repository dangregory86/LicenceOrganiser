package gregory.dan.licenceorganiser.notifications;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Daniel Gregory on 06/09/2018.
 */
public class App extends Application {

    public static final String CHANNEL_1 = "gregory.dan.licenceorganiser.channel1";

    @Override
    public void onCreate() {
        super.onCreate();
        createChannels();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private void createChannels(){

        String twtydayreminder = "28 Day reminder";
        String twtyDayReminderDesc = "A reminder to chase 28 day action points.";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1,
                    twtydayreminder,
                            NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setDescription(twtyDayReminderDesc);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }
    }
}
