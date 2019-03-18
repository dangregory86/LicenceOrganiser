package gregory.dan.licenceorganiser.services;

import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import gregory.dan.licenceorganiser.Constants;

public class UpdateService extends GcmTaskService {
    @Override
    public int onRunTask(TaskParams taskParams) {

        String taskId = taskParams.getExtras().getString(Constants.UPDATE_SERVICE_ID);


        return 0;
    }
}
