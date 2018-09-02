package gregory.dan.licenceorganiser.Unit.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import gregory.dan.licenceorganiser.Unit.Ammunition;
import gregory.dan.licenceorganiser.Unit.Licence;
import gregory.dan.licenceorganiser.Unit.OutstandingPoints;
import gregory.dan.licenceorganiser.Unit.Unit;
import gregory.dan.licenceorganiser.Unit.database.AppRepository;

/**
 * Created by Daniel Gregory on 31/08/2018.
 */
public class MyViewModel extends AndroidViewModel {

    private AppRepository mRepository;
    private LiveData<List<Unit>> mAllUnits;

    public MyViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AppRepository(application);
        mAllUnits = mRepository.getAllUnits();
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
    public LiveData<List<OutstandingPoints>> getAllUnitPoints(String unitTitle) {
        return mRepository.getAllUnitPoints(unitTitle);
    }

    public void insertPoint(OutstandingPoints point) {
        insertPoint(point);
    }

    public void deletePoint(OutstandingPoints point) {
        mRepository.deletePoint(point);
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

    public Licence getIndividualLicence(String licenceSerialNo){
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
