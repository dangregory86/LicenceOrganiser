package gregory.dan.licenceorganiser.Unit.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import gregory.dan.licenceorganiser.Unit.OutstandingPoints;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Daniel Gregory on 31/08/2018.
 */
@Dao
public interface OutstandingPointsDao {

    @Query("SELECT * FROM OutstandingPoints WHERE inspection_date=:date")
    LiveData<List<OutstandingPoints>> getOutstandingPoints(long date);

    @Insert(onConflict = REPLACE)
    void insertPoint(OutstandingPoints point);

    @Update(onConflict = REPLACE)
    void updatePoint(OutstandingPoints point);

    @Delete
    void deletePoint(OutstandingPoints point);
}
