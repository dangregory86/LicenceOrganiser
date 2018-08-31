package gregory.dan.licenceorganiser.Unit.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import gregory.dan.licenceorganiser.Unit.Ammunition;
import gregory.dan.licenceorganiser.Unit.Licence;
import gregory.dan.licenceorganiser.Unit.OutstandingPoints;
import gregory.dan.licenceorganiser.Unit.Unit;
import gregory.dan.licenceorganiser.Unit.daos.AmmunitionDao;
import gregory.dan.licenceorganiser.Unit.daos.LicenceDao;
import gregory.dan.licenceorganiser.Unit.daos.OutstandingPointsDao;
import gregory.dan.licenceorganiser.Unit.daos.UnitDao;

/**
 * Created by Daniel Gregory on 31/08/2018.
 */
public class AppRepository {

    private AppDatabase mDatabase;
    private LiveData<List<Unit>> mUnits;

    public AppRepository(Application application) {
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

    public Unit loadUnitWithName(String unitName) {
        return mDatabase.unitModel().loadUnitByTitle(unitName);
    }

    /*
     * The following functions all link to the OutstandingPointsDao
     * */
    public LiveData<List<OutstandingPoints>> getAllUnitPoints(String unitTitle) {
        return mDatabase.pointsModel().getOutstandingPoints(unitTitle);
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
}
