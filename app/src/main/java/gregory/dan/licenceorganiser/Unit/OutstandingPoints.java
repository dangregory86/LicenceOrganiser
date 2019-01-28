package gregory.dan.licenceorganiser.Unit;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Daniel Gregory on 26/08/2018.
 */
@Entity(foreignKeys = {
        @ForeignKey(onDelete = CASCADE,
                entity = Inspection.class,
        parentColumns = "_id",
        childColumns = "inspection_id")
}, indices = @Index("inspection_id"))
public class OutstandingPoints{

    @PrimaryKey
    @NonNull
    public long id;

    @ColumnInfo(name = "inspection_id")
    public long inspectionId;

    public String point;

    public long complete;

    public OutstandingPoints(long inspectionId, String point, long complete, long id) {
        this.inspectionId = inspectionId;
        this.point = point;
        this.complete = complete;
        this.id = id;
    }
}
