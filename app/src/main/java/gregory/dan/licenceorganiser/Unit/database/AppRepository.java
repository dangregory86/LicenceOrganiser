package gregory.dan.licenceorganiser.Unit.database;

import android.app.Application;
import android.app.NotificationManager;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import gregory.dan.licenceorganiser.Unit.Ammunition;
import gregory.dan.licenceorganiser.Unit.Inspection;
import gregory.dan.licenceorganiser.Unit.Licence;
import gregory.dan.licenceorganiser.Unit.OutstandingPoints;
import gregory.dan.licenceorganiser.Unit.Unit;
import gregory.dan.licenceorganiser.Unit.daos.AmmunitionDao;
import gregory.dan.licenceorganiser.Unit.daos.InspectionDao;
import gregory.dan.licenceorganiser.Unit.daos.LicenceDao;
import gregory.dan.licenceorganiser.Unit.daos.OutstandingPointsDao;
import gregory.dan.licenceorganiser.Unit.daos.UnitDao;
import gregory.dan.licenceorganiser.firebase.FireBaseDatabaseUtilities;

/**
 * Created by Daniel Gregory on 31/08/2018.
 */
public class AppRepository {

    public static final String UNIT_REF_TEXT = "units";
    public static final String LICENCE_REF_TEXT = "Licences";
    public static final String POINTS_REF_TEXT = "Points";
    public static final String INSPECTIONS_REF_TEXT = "Inspections";
    public static final String AMMUNITION_REF_TEXT = "Ammunition";

    private FireBaseDatabaseUtilities mDatabaseUtils;

    private AppDatabase mDatabase;
    private LiveData<List<Unit>> mUnits;
    private Application application;

    public AppRepository(Application application) {
        this.application = application;
        mDatabaseUtils = new FireBaseDatabaseUtilities();
        mDatabase = AppDatabase.getInMemoryDatabase(application);
        mUnits = mDatabase.unitModel().loadAllUnits();
    }

    /*
     * The following functions all link to the UnitDao
     *
     * */
    public LiveData<List<Unit>> getAllUnits() {
        return mUnits;
    }


    public void insertUnit(Unit unit) {
        new insertUnitAsyncTask(mDatabase.unitModel()).execute(unit);
    }

    private static class insertUnitAsyncTask extends AsyncTask<Unit, Void, Void> {
        private UnitDao aSyncDao;

        insertUnitAsyncTask(UnitDao unitDao) {
            aSyncDao = unitDao;
        }

        @Override
        protected Void doInBackground(Unit... units) {
            aSyncDao.insertUnit(units[0]);
            return null;
        }
    }

    public void deleteUnit(Unit unit) {
        new DeleteUnitAsyncTask(mDatabase.unitModel()).execute(unit);
    }

    private static class DeleteUnitAsyncTask extends AsyncTask<Unit, Void, Void> {
        private UnitDao aSyncDao;

        DeleteUnitAsyncTask(UnitDao unitDao) {
            aSyncDao = unitDao;
        }

        @Override
        protected Void doInBackground(Unit... units) {
            aSyncDao.insertUnit(units[0]);
            return null;
        }
    }

    public void deleteAllUnits() {
        new DeleteAllUnitsAsyncTask(mDatabase.unitModel()).execute();
    }

    private static class DeleteAllUnitsAsyncTask extends AsyncTask<Void, Void, Void> {
        private UnitDao aSyncDao;

        DeleteAllUnitsAsyncTask(UnitDao unitDao) {
            aSyncDao = unitDao;
        }

        @Override
        protected Void doInBackground(Void... units) {
            aSyncDao.deleteAllUnits();
            return null;
        }
    }

    public void updateUnit(Unit unit) {
        new UpdateUnitAsyncTask(mDatabase.unitModel()).execute(unit);
    }

    private static class UpdateUnitAsyncTask extends AsyncTask<Unit, Void, Void> {
        private UnitDao asyncDao;

        UpdateUnitAsyncTask(UnitDao unitDao) {
            asyncDao = unitDao;
        }

        @Override
        protected Void doInBackground(Unit... units) {
            asyncDao.updateUnit(units[0]);
            return null;
        }
    }

    public Unit loadUnitWithName(String unitName) {
        return mDatabase.unitModel().loadUnitByTitle(unitName);
    }

    /*
     * The following functions all link to the OutstandingPointsDao
     * */
    public LiveData<List<OutstandingPoints>> getAllUnitPoints(long id) {
        return mDatabase.pointsModel().getOutstandingPoints(id);
    }

    public void insertPoint(OutstandingPoints point) {
        new insertPointAsyncTask(mDatabase.pointsModel()).execute(point);
    }

    private static class insertPointAsyncTask extends AsyncTask<OutstandingPoints, Void, Void> {
        private OutstandingPointsDao aSyncDao;

        insertPointAsyncTask(OutstandingPointsDao pointDao) {
            aSyncDao = pointDao;
        }

        @Override
        protected Void doInBackground(OutstandingPoints... points) {
            aSyncDao.insertPoint(points[0]);
            return null;
        }
    }

    public void updatePoint(OutstandingPoints point) {
        new UpdatePointAsyncTask(mDatabase.pointsModel()).execute(point);
    }

    private static class UpdatePointAsyncTask extends AsyncTask<OutstandingPoints, Void, Void> {
        private OutstandingPointsDao asyncDao;

        UpdatePointAsyncTask(OutstandingPointsDao dao) {
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(OutstandingPoints... points) {
            asyncDao.updatePoint(points[0]);
            return null;
        }
    }

    public void deletePoint(OutstandingPoints point) {
        new DeletePointAsyncTask(mDatabase.pointsModel()).execute(point);
    }

    private static class DeletePointAsyncTask extends AsyncTask<OutstandingPoints, Void, Void> {
        private OutstandingPointsDao aSyncDao;

        DeletePointAsyncTask(OutstandingPointsDao pointDao) {
            aSyncDao = pointDao;
        }

        @Override
        protected Void doInBackground(OutstandingPoints... points) {
            aSyncDao.insertPoint(points[0]);
            return null;
        }
    }

    /*
     * The following functions all link to the InspectionDao
     *
     * */

    public LiveData<List<Inspection>> getAllPreviousInspectionsFromUnit(String unitName) {
        return mDatabase.inspectionModel().getInspections(unitName);
    }

    public Inspection getInspection(long id) {
        return mDatabase.inspectionModel().getInspection(id);
    }

    public void insertInspection(Inspection inspection) {
        new InsertInspectionAsyncTask(mDatabase.inspectionModel()).execute(inspection);
    }

    private static class InsertInspectionAsyncTask extends AsyncTask<Inspection, Void, Void> {
        private InspectionDao dao;

        InsertInspectionAsyncTask(InspectionDao inspectionDao) {
            dao = inspectionDao;
        }

        @Override
        protected Void doInBackground(Inspection... inspections) {
            dao.insertInspection(inspections[0]);
            return null;
        }
    }

    public void updateInspection(Inspection inspection) {
        new UpdateInspectionAsyncTask(mDatabase.inspectionModel()).execute(inspection);
    }

    private static class UpdateInspectionAsyncTask extends AsyncTask<Inspection, Void, Void> {
        private InspectionDao dao;
        private int numPoints;
        private int inspId;

        public UpdateInspectionAsyncTask(InspectionDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Inspection... inspections) {
            dao.updateInspection(inspections[0]);
            return null;
        }
    }

    /*
     * The following functions all link to the LicenceDao
     *
     * */
    public LiveData<List<Licence>> getAllUnitLicences(String unitName) {
        return mDatabase.licenceModel().getLicences(unitName);
    }

    public void insertLicence(Licence licence) {
        new InsertLicenceAsyncTask(mDatabase.licenceModel()).execute(licence);
    }

    private static class InsertLicenceAsyncTask extends AsyncTask<Licence, Void, Void> {
        private LicenceDao asyncDao;

        InsertLicenceAsyncTask(LicenceDao licenceDao) {
            asyncDao = licenceDao;
        }

        @Override
        protected Void doInBackground(Licence... licences) {
            asyncDao.insertLicence(licences[0]);
            return null;
        }
    }

    public void deleteUnitLicence(Licence licence) {
        new DeleteUnitLicenceAsyncTask(mDatabase.licenceModel()).execute(licence);
    }

    private static class DeleteUnitLicenceAsyncTask extends AsyncTask<Licence, Void, Void> {
        private LicenceDao asyncDao;

        DeleteUnitLicenceAsyncTask(LicenceDao licenceDao) {
            asyncDao = licenceDao;
        }

        @Override
        protected Void doInBackground(Licence... licences) {
            asyncDao.deleteLicence(licences[0]);
            return null;
        }
    }

    public void updateLicence(Licence licence) {
        new UpdateLicenceAsyncTask(mDatabase.licenceModel()).execute(licence);
    }

    private static class UpdateLicenceAsyncTask extends AsyncTask<Licence, Void, Void> {
        private LicenceDao dao;

        UpdateLicenceAsyncTask(LicenceDao licenceDao) {
            dao = licenceDao;
        }

        @Override
        protected Void doInBackground(Licence... licences) {
            dao.updateLicence(licences[0]);
            return null;
        }
    }

    public Licence getIndividualLicence(String licenceSerialNo) {
        return mDatabase.licenceModel().getIndividualLicence(licenceSerialNo);
    }

    /*
     * The following functions all link to the AmmunitionDao
     *
     * */
    public LiveData<List<Ammunition>> getAllUnitAmmunition(String licence_serial) {
        return mDatabase.ammunitionModel().getAmmunition(licence_serial);
    }

    public void insertAmmunition(Ammunition ammunition) {
        new InsertAmmunitionAsyncTask(mDatabase.ammunitionModel()).execute(ammunition);
    }

    private static class InsertAmmunitionAsyncTask extends AsyncTask<Ammunition, Void, Void> {
        private AmmunitionDao dao;

        InsertAmmunitionAsyncTask(AmmunitionDao ammunitionDao) {
            dao = ammunitionDao;
        }

        @Override
        protected Void doInBackground(Ammunition... ammunitions) {
            dao.insertAmunition(ammunitions[0]);
            return null;
        }
    }

    public void deleteAmmunition(Ammunition ammunition) {
        new DeleteAmmunitionAsyncTask(mDatabase.ammunitionModel()).execute(ammunition);
    }

    private static class DeleteAmmunitionAsyncTask extends AsyncTask<Ammunition, Void, Void> {
        private AmmunitionDao dao;

        DeleteAmmunitionAsyncTask(AmmunitionDao ammunitionDao) {
            dao = ammunitionDao;
        }

        @Override
        protected Void doInBackground(Ammunition... ammunitions) {
            dao.deleteAmmunition(ammunitions[0]);
            return null;
        }
    }

    /*Firebase operations*/
    public void inseertOrUpdateFirebaseUnit(final Unit unit) {
        mDatabaseUtils.inseertOrUpdateFirebaseUnit(unit);
    }

    public void inseertOrUpdateFirebaseLicence(final Licence licence) {
        mDatabaseUtils.inseertOrUpdateFirebaseLicence(licence);
    }

    public void inseertOrUpdateFirebaseAmmunition(final Ammunition ammunition) {
        mDatabaseUtils.inseertOrUpdateFirebaseAmmunition(ammunition);
    }

    public void inseertOrUpdateFirebaseInspection(final Inspection inspection) {
        mDatabaseUtils.inseertOrUpdateFirebaseInspection(inspection);
    }

    public void inseertOrUpdateFirebasePoint(final OutstandingPoints point) {
        mDatabaseUtils.inseertOrUpdateFirebasePoint(point);
    }

    public void deleteAmmunitionFirebase(long id) {
        mDatabaseUtils.deleteAmmo(id);
    }

    public void deleteLicenceFirebase(final Licence licence) {
        String unit = licence.unitTitle;
        String serial = licence.licenceSerial;
        cancelNotification(unit + serial);
        mDatabaseUtils.deleteLicence(licence.licenceSerial);
    }

    public void deleteUnitFirebase(final Unit unit) {
        mDatabaseUtils.deleteUnit(unit.unitTitle);
    }

    public void deleteInspectionFirebase(final Inspection inspection) {
        String unit = inspection.unit;
        String inspectedBy = inspection.inspectedBy;
        String date = new SimpleDateFormat("dd-MMMM-YYYY", Locale.getDefault()).format(inspection.inspectionDate);
        cancelNotification(unit + inspectedBy + date + "1");
        cancelNotification(unit + inspectedBy + date + "2");
        mDatabaseUtils.deleteInspection(inspection._id);
    }

    public void deleteInspectionPointFirebase(final OutstandingPoints point) {
        mDatabaseUtils.deletePoint(point.id);
    }

    private void cancelNotification(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(application);
        int notiId = preferences.getInt(key, 0);
        NotificationManager notificationManager = (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notiId);
    }

}
