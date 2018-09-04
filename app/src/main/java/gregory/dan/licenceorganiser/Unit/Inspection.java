package gregory.dan.licenceorganiser.Unit;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

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
public class Inspection {

    @PrimaryKey
    public long _id;

    @ColumnInfo(name = "unit")
    public String unit;

    public long hasPoints;

    public long inspectionDate;

    public long reminderDate;

    public long nextInspectionDue;

    public String inspectedBy;

    public Inspection(String unit, long hasPoints, long inspectionDate, long reminderDate, long nextInspectionDue, String inspectedBy, long id) {
        this.unit = unit;
        this.hasPoints = hasPoints;
        this.inspectionDate = inspectionDate;
        this.reminderDate = reminderDate;
        this.nextInspectionDue = nextInspectionDue;
        this.inspectedBy = inspectedBy;
        this._id = id;
    }

}
