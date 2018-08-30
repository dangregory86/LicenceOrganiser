package gregory.dan.licenceorganiser.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Daniel Gregory on 30/08/2018.
 */
@Database(entities = {UnitRoomClass.class}, version = 1)
public abstract class UnitRoomDatabase extends RoomDatabase {
    public abstract UnitDao unitDao();

    private static UnitRoomDatabase INSTANCE;

    public static UnitRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (UnitRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UnitRoomDatabase.class,
                            "unit_table")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
