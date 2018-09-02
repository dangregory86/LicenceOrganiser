package gregory.dan.licenceorganiser.Unit.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import gregory.dan.licenceorganiser.Unit.Inspection;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Daniel Gregory on 02/09/2018.
 */
@Dao
public interface InspectionDao {

    @Query("SELECT * FROM Inspection WHERE unit=:unitName")
    LiveData<List<Inspection>> getInspections(String unitName);

    @Insert(onConflict = REPLACE)
    void insertInspection(Inspection inspection);

    @Update(onConflict = REPLACE)
    void updateInspection(Inspection inspection);

}
