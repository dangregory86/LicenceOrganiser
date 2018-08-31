package gregory.dan.licenceorganiser.Unit.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import gregory.dan.licenceorganiser.Unit.DateConverter;
import gregory.dan.licenceorganiser.Unit.OutstandingPoints;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Daniel Gregory on 31/08/2018.
 */
@Dao
@TypeConverters(DateConverter.class)
public interface OutstandingPointsDao {

    @Query("SELECT * FROM OutstandingPoints WHERE unit=:unitName")
    LiveData<List<OutstandingPoints>> getOutstandingPoints(String unitName);

    @Insert(onConflict = REPLACE)
    void insertPoint(OutstandingPoints point);

    @Delete
    void deletePoint(OutstandingPoints points);
}
