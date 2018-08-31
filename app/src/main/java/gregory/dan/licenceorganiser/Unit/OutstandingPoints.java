package gregory.dan.licenceorganiser.Unit;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Daniel Gregory on 26/08/2018.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Unit.class,
        parentColumns = "unitTitle",
        childColumns = "unit")
}, indices = @Index("unit"))
@TypeConverters(DateConverter.class)
public class OutstandingPoints{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "unit")
    public String unitTitle;

    public String point;

    public Date inspectionDate;

    public Date reminderDate;

    public OutstandingPoints(String unitTitle, String point, Date inspectionDate, Date reminderDate) {
        this.unitTitle = unitTitle;
        this.point = point;
        this.inspectionDate = inspectionDate;
        this.reminderDate = reminderDate;
    }
}
