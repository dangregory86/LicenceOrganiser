package gregory.dan.licenceorganiser.Unit;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Daniel Gregory on 26/08/2018.
 */
@Entity(foreignKeys = {
        @ForeignKey(onDelete = CASCADE,
                entity = Inspection.class,
        parentColumns = "inspectionDate",
        childColumns = "inspection_date")
}, indices = @Index("inspection_date"))
@TypeConverters(DateConverter.class)
public class OutstandingPoints{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "inspection_date")
    public Date inspectionDate;

    public String point;

    public int complete;

    public OutstandingPoints(Date inspectionDate, String point, int complete) {
        this.inspectionDate = inspectionDate;
        this.point = point;
        this.complete = complete;
    }
}
