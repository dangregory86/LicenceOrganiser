package gregory.dan.licenceorganiser.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Daniel Gregory on 30/08/2018.
 */
@Dao
public interface UnitDao {

    @Insert(onConflict = REPLACE)
    void insert(UnitRoomClass unit);

    @Update(onConflict = REPLACE)
    void updateUnit(UnitRoomClass unit);

    @Query("DELETE FROM unit_table")
    void deleteAllUnits();

    @Query("SELECT * FROM unit_table")
    LiveData<List<UnitRoomClass>> getAllUnits();

    @Query("SELECT * FROM unit_table WHERE unit_title=:unitName")
    UnitRoomClass getUnit(String unitName);

    @Query("UPDATE unit_table SET unit_points = :points WHERE unit_title=:unitName")
    int updatePoints(String unitName, String points);

    @Query("UPDATE unit_table SET unit_licences = :licences WHERE unit_title=:unitName")
    int updateLicences(String unitName, String licences);


}
