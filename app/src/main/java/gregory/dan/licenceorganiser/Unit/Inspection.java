package gregory.dan.licenceorganiser.Unit;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Daniel Gregory on 02/09/2018.
 */
@Entity(foreignKeys = {
        @ForeignKey(onDelete = CASCADE,
                entity = Unit.class,
                parentColumns = "unitTitle",
                childColumns = "unit")
}, indices = @Index("unit"))
@TypeConverters(DateConverter.class)
public class Inspection {

    @ColumnInfo(name = "unit")
    public String unit;

    public int hasPoints;

    @PrimaryKey
    public Date inspectionDate;

    public Date reminderDate;

    public Date nextInspectionDue;

    public Inspection(String unit, int hasPoints, Date inspectionDate, Date reminderDate, Date nextInspectionDue) {
        this.unit = unit;
        this.hasPoints = hasPoints;
        this.inspectionDate = inspectionDate;
        this.reminderDate = reminderDate;
        this.nextInspectionDue = nextInspectionDue;
    }

}
