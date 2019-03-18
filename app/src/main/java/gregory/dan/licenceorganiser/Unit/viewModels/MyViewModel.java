package gregory.dan.licenceorganiser.Unit.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import static gregory.dan.licenceorganiser.Unit.database.AppRepository.POINTS_REF_TEXT;
import static gregory.dan.licenceorganiser.Unit.database.AppRepository.UNIT_REF_TEXT;

/**
 * Created by Daniel Gregory on 31/08/2018.
 */
public class MyViewModel extends AndroidViewModel {

    private AppRepository mRepository;
    private WorkManager mWorkManager;
    private FirebaseDatabase mDatabase;
    private FirebaseQueryLiveData mFirebaseQueryLiveData;

    public MyViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AppRepository(application);
        mDatabase = FirebaseDatabase.getInstance();
        mWorkManager = WorkManager.getInstance();
    }

    private void observeFirebaseUnits(){
        DatabaseReference ref = mDatabase.getReference(UNIT_REF_TEXT);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String unitTitle = (String) data.child("unitTitle").getValue();
                        String unitAddress = (String) data.child("unitAddress").getValue();
                        String unitContactNumber = (String) data.child("unitContactNumber").getValue();
                        String unitCO = (String) data.child("unitCO").getValue();
                        insertUnit(new Unit(unitTitle,
                                unitAddress,
                                unitContactNumber,
                                unitCO));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

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

    public LiveData<DataSnapshot> getmAllUnits() {
        DatabaseReference ref = mDatabase.getReference(UNIT_REF_TEXT);
        mFirebaseQueryLiveData = new FirebaseQueryLiveData(ref);
        return mFirebaseQueryLiveData;
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
    public LiveData<DataSnapshot> getAllUnitPoints() {
        DatabaseReference ref = mDatabase.getReference(POINTS_REF_TEXT);
        mFirebaseQueryLiveData = new FirebaseQueryLiveData(ref);
        return mFirebaseQueryLiveData;
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
