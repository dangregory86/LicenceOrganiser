package gregory.dan.licenceorganiser.notifications;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.util.Objects;

import gregory.dan.licenceorganiser.R;

/**
 * Created by Daniel Gregory on 06/09/2018.
 */
public class App extends Application {

    public static final String CHANNEL_1 = "gregory.dan.licenceorganiser.channel1";

    @Override
    public void onCreate() {
        super.onCreate();
        createChannels();
    }

    public void createChannels() {

        String twtydayreminder = getString(R.string.twenty_eight_day_reminder_title);
        String twtyDayReminderDesc = getString(R.string.twenty_eight_day_reminder_description);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1,
                    twtydayreminder,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setDescription(twtyDayReminderDesc);

            NotificationManager manager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(manager).createNotificationChannel(channel1);

        }
    }
}
