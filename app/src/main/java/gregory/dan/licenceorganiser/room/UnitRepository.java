package gregory.dan.licenceorganiser.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Daniel Gregory on 30/08/2018.
 */
public class UnitRepository {

    private UnitDao mUnitDao;
    private LiveData<List<UnitRoomClass>> mUnits;

    public UnitRepository(Application application){
        UnitRoomDatabase db = UnitRoomDatabase.getDatabase(application);
        mUnitDao = db.unitDao();
        mUnits = mUnitDao.getAllUnits();
    }

    public LiveData<List<UnitRoomClass>> getAllUnits(){
        return mUnits;
    }

    public void insert(UnitRoomClass unit){
        new insertAsyncTask(mUnitDao).execute(unit);
    }

    private static class insertAsyncTask extends AsyncTask<UnitRoomClass, Void, Void>{

        private UnitDao unitDao;

        insertAsyncTask(UnitDao mUnitDao){
            unitDao = mUnitDao;
        }
        @Override
        protected Void doInBackground(final UnitRoomClass... unitRoomClasses) {
            unitDao.insert(unitRoomClasses[0]);
            return null;
        }
    }
}
