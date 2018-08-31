package gregory.dan.licenceorganiser.Unit.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

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
@Database(entities = {Unit.class, Licence.class, OutstandingPoints.class, Ammunition.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UnitDao unitModel();

    public abstract LicenceDao licenceModel();

    public abstract OutstandingPointsDao pointsModel();

    public abstract AmmunitionDao ammunitionModel();

    public static AppDatabase getInMemoryDatabase(Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class,
                                    "app_database")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

}
