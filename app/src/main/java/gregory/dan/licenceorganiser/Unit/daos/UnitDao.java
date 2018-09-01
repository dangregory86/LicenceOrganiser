package gregory.dan.licenceorganiser.Unit.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import gregory.dan.licenceorganiser.Unit.Unit;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Daniel Gregory on 31/08/2018.
 */
@Dao
public interface UnitDao {
    @Query("SELECT * FROM Unit")
    LiveData<List<Unit>> loadAllUnits();

    @Query("SELECT * FROM Unit WHERE unitTitle=:unitName")
    Unit loadUnitByTitle(String unitName);

    @Insert(onConflict = REPLACE)
    void insertUnit(Unit unit);

    @Delete
    void deleteUnit(Unit unit);

    @Update(onConflict = REPLACE)
    void updateUnit(Unit unit);

    @Query("DELETE FROM Unit")
    void deleteAllUnits();
}
