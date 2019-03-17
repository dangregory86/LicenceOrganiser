package gregory.dan.licenceorganiser.Unit.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.List;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import gregory.dan.licenceorganiser.Unit.Ammunition;
import gregory.dan.licenceorganiser.Unit.Inspection;
import gregory.dan.licenceorganiser.Unit.Licence;
import gregory.dan.licenceorganiser.Unit.OutstandingPoints;
import gregory.dan.licenceorganiser.Unit.Unit;
import gregory.dan.licenceorganiser.Unit.database.AppRepository;
import gregory.dan.licenceorganiser.workmanagers.DeleteFromFirebaseWorker;
import gregory.dan.licenceorganiser.workmanagers.UpdateFirebaseWorkManager;

import static gregory.dan.licenceorganiser.Constants.OBJECT_DATA_KEY;
import static gregory.dan.licenceorganiser.Constants.OBJECT_TYPE;

/**
 * Created by Daniel Gregory on 31/08/2018.
 */
public class MyViewModel extends AndroidViewModel {

    private AppRepository mRepository;
    private LiveData<List<Unit>> mAllUnits;
    WorkManager mWorkManager;

    public MyViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AppRepository(application);
        mAllUnits = mRepository.getAllUnits();
        mWorkManager = WorkManager.getInstance();
    }

//    public void insertToFirebase(Object object){
//        if(Unit.class.isInstance(object)){
//            mRepository.inseertOrUpdateFirebaseUnit((Unit) object);
//        }else if(OutstandingPoints.class.isInstance(object)){
//            mRepository.inseertOrUpdateFirebasePoint((OutstandingPoints) object);
//        }else if(Licence.class.isInstance(object)){
//            mRepository.inseertOrUpdateFirebaseLicence((Licence) object);
//        }else if(Inspection.class.isInstance(object)){
//            mRepository.inseertOrUpdateFirebaseInspection((Inspection) object);
//        }else if(Ammunition.class.isInstance(object)){
//            mRepository.inseertOrUpdateFirebaseAmmunition((Ammunition) object);
//        }
//    }

    public void insertToFirebase(Object object) {
        Data data = mData(object);

        if (data != null) {
            OneTimeWorkRequest simpleRequest = new OneTimeWorkRequest.Builder(UpdateFirebaseWorkManager.class)
                    .setInputData(data)
                    .build();
            WorkManager.getInstance().enqueue(simpleRequest);
        }
    }

    /*
     * Function to turn the object data into  json string and return it for the work manager*/
    private Data mData(Object object) {
        //create a gson object
        Gson gson = new Gson();
        String data = "";
        data = gson.toJson(object);

        Data.Builder builder = new Data.Builder();
        if (data != null || data.equals("")) {

            if (object instanceof Unit) {
                builder.putString(OBJECT_TYPE, "unit");
            } else if (object instanceof OutstandingPoints) {
                builder.putString(OBJECT_TYPE, "outstanding_point");
            } else if (object instanceof Licence) {
                builder.putString(OBJECT_TYPE, "licence");
            } else if (object instanceof Inspection) {
                builder.putString(OBJECT_TYPE, "inspection");
            } else if (object instanceof Ammunition) {
                builder.putString(OBJECT_TYPE, "ammunition");
            }

            builder.putString(OBJECT_DATA_KEY, data);
            return builder.build();
        }
        return null;
    }

    public void deleteFromFirebase(Object object) {

        Data data = mData(object);

        if (data != null) {
            OneTimeWorkRequest simpleRequest = new OneTimeWorkRequest.Builder(DeleteFromFirebaseWorker.class)
                    .setInputData(data)
                    .build();
            WorkManager.getInstance().enqueue(simpleRequest);
        }
    }

    public LiveData<List<Unit>> getmAllUnits() {
        return mAllUnits;
    }

    public void insertUnit(Unit unit) {
        mRepository.insertUnit(unit);
    }

    public void deleteUnit(Unit unit) {
        mRepository.deleteUnit(unit);
    }

    public void updateUnit(Unit unit) {
        mRepository.updateUnit(unit);
    }

    public void deleteAllUnits() {
        mRepository.deleteAllUnits();
    }

    public Unit loadUnitWithName(String unitName) {
        return mRepository.loadUnitWithName(unitName);
    }

    /*
     * The following functions all link to the OutstandingPointsDao
     * */
    public LiveData<List<OutstandingPoints>> getAllUnitPoints(long inspectionId) {
        return mRepository.getAllUnitPoints(inspectionId);
    }

    public void insertPoint(OutstandingPoints point) {
        mRepository.insertPoint(point);
    }

    public void updatePoint(OutstandingPoints point) {
        mRepository.updatePoint(point);
    }

    public void deletePoint(OutstandingPoints point) {
        mRepository.deletePoint(point);
    }

    /*
     * The following functions all link to the LicenceDao
     *
     * */

    public LiveData<List<Inspection>> getAllPreviousInspections(String unitName) {
        return mRepository.getAllPreviousInspectionsFromUnit(unitName);
    }

    public void insertInspection(Inspection inspection) {
        mRepository.insertInspection(inspection);
    }

    public void updateInspection(Inspection inspection) {
        mRepository.updateInspection(inspection);
    }

    public Inspection getInspection(long id) {
        return mRepository.getInspection(id);
    }

    /*
     * The following functions all link to the LicenceDao
     *
     * */
    public LiveData<List<Licence>> getAllUnitLicences(String unitName) {
        return mRepository.getAllUnitLicences(unitName);
    }

    public void insertLicence(Licence licence) {
        mRepository.insertLicence(licence);
    }

    public void deleteUnitLicence(Licence licence) {
        mRepository.deleteUnitLicence(licence);
    }

    public void updateLicence(Licence licence) {
        mRepository.updateLicence(licence);
    }

    public Licence getIndividualLicence(String licenceSerialNo) {
        return mRepository.getIndividualLicence(licenceSerialNo);
    }

    /*
     * The following functions all link to the AmmunitionDao
     *
     * */
    public LiveData<List<Ammunition>> getAllUnitAmmunition(String licence_serial) {
        return mRepository.getAllUnitAmmunition(licence_serial);
    }

    public void insertAmmunition(Ammunition ammunition) {
        mRepository.insertAmmunition(ammunition);
    }

    public void deleteAmmunition(Ammunition ammunition) {
        mRepository.deleteAmmunition(ammunition);
    }

}
