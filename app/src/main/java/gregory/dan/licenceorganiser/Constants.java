package gregory.dan.licenceorganiser;

public class Constants {

    // Notification Channel constants

    // Name of Notification Channel for verbose notifications of background work
    public static final CharSequence VERBOSE_NOTIFICATION_CHANNEL_NAME =
            "Verbose WorkManager Notifications";
    public static String VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
            "Shows notifications whenever work starts";
    public static final CharSequence NOTIFICATION_TITLE = "WorkRequest Starting";
    public static final String CHANNEL_ID = "VERBOSE_NOTIFICATION" ;
    public static final int NOTIFICATION_ID = 1;

    //viewmodal constants
    public static final String OBJECT_DATA_KEY = "gregory.dan.licenceorganiser.Unit.viewModels.objectdatakey";
    public static final String OBJECT_TYPE = "gregory.dan.licenceorganiser.Unit.viewModels.objecttype";

    // update service
    public static final String UPDATE_SERVICE_ID = "gregory.dan.licenceorganiser.services.UpdateService.update.id";

    // Ensures this class is never instantiated
    private Constants() {}
}
