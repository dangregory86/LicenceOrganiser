package gregory.dan.licenceorganiser.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Date;

import gregory.dan.licenceorganiser.MainActivity;
import gregory.dan.licenceorganiser.R;
import gregory.dan.licenceorganiser.ViewUnitActivity;

import static gregory.dan.licenceorganiser.AddUnitActivity.UNIT_NAME_EXTRA;
import static gregory.dan.licenceorganiser.notifications.App.CHANNEL_1;

public class NotificationService extends Service {

    public static final String UNIT_TITLE_NOTIFICATION_EXTRA = "gregory.dan.licenceorgansider.notifications.unit.name";
    public static final String MESSAGE_NOTIFICATION_EXTRA = "gregory.dan.licenceorgansider.notifications.message";
    public static final String ALERT_NOTIFICATION_EXTRA = "gregory.dan.licenceorgansider.notifications.alert";
    public static final int NOTIFICATION_ID = 9431;
    public static final String ID_EXTRA = "gregory.dan.licenceorganiser.id.extra";

    private String unitTitle;
    private String message;
    private String alert;
    private String id;


    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra(UNIT_TITLE_NOTIFICATION_EXTRA) && intent.hasExtra(ID_EXTRA)) {
            if (intent.hasExtra(MESSAGE_NOTIFICATION_EXTRA) &&
                    intent.hasExtra(ALERT_NOTIFICATION_EXTRA)) {
                message = intent.getStringExtra(MESSAGE_NOTIFICATION_EXTRA);
                alert = intent.getStringExtra(ALERT_NOTIFICATION_EXTRA);
            }
            unitTitle = intent.getStringExtra(UNIT_TITLE_NOTIFICATION_EXTRA);
            id = intent.getStringExtra(ID_EXTRA);
            showNotification();
        }
        return startId;
    }

    @Override
    public void onCreate() {
        message = getResources().getString(R.string.twentyeightdayreminder);
        alert = getResources().getString(R.string.twentyeightdayalert);
    }

    private void showNotification() {
        Intent mainIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(mainIntent);
        Intent notIntent = new Intent(this, ViewUnitActivity.class);
        notIntent.putExtra(UNIT_NAME_EXTRA, unitTitle);
        taskStackBuilder.addNextIntent(notIntent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(NOTIFICATION_ID, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1)
                .setSmallIcon(R.drawable.ic_assignment_late_black_24dp)
                .setAutoCancel(true)
                .setTicker(alert)
                .setContentTitle(unitTitle)
                .setContentText(message)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setContentIntent(pendingIntent)
                .build();

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(unitTitle + id, m);
        managerCompat.notify(m, notification);
    }
}
