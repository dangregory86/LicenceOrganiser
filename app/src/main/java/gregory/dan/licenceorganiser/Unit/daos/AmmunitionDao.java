package gregory.dan.licenceorganiser.Unit.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import gregory.dan.licenceorganiser.Unit.Ammunition;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Daniel Gregory on 31/08/2018.
 */
@Dao
public interface AmmunitionDao {

    @Query("SELECT * FROM Ammunition WHERE licence_serial=:serial")
    LiveData<List<Ammunition>> getAmmunition(String serial);

    @Insert(onConflict = REPLACE)
    void insertAmunition(Ammunition ammunition);

    @Delete
    void deleteAmmunition(Ammunition ammunition);
}
