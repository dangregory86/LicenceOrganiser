package gregory.dan.licenceorganiser.Unit.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;

import gregory.dan.licenceorganiser.Unit.DateConverter;
import gregory.dan.licenceorganiser.Unit.Licence;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Daniel Gregory on 31/08/2018.
 */
@Dao
@TypeConverters(DateConverter.class)
public interface LicenceDao {

    @Query("SELECT * FROM Licence WHERE unit=:unitName")
    LiveData<List<Licence>> getLicences(String unitName);

    @Query("SELECT * FROM Licence WHERE licenceSerial=:licence")
    Licence getIndividualLicence(String licence);

    @Update(onConflict = REPLACE)
    void updateLicence(Licence licence);

    @Insert(onConflict = REPLACE)
    void insertLicence(Licence licence);

    @Delete
    void deleteLicence(Licence licence);
}
