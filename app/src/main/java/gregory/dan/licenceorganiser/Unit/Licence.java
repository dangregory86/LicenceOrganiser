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
                entity = Unit.class,
        parentColumns = "unitTitle",
        childColumns = "unit")
}, indices = @Index("unit"))
@TypeConverters(DateConverter.class)
public class Licence{

    @PrimaryKey
    @NonNull
    public String licenceSerial;

    @ColumnInfo(name = "unit")
    public String unitTitle;

    public String licenceType;
    public Date licenceIssueDate;
    public Date licenceRenewalDate;

    public Licence(@NonNull String licenceSerial, String unitTitle, String licenceType, Date licenceIssueDate, Date licenceRenewalDate) {
        this.licenceSerial = licenceSerial;
        this.unitTitle = unitTitle;
        this.licenceType = licenceType;
        this.licenceIssueDate = licenceIssueDate;
        this.licenceRenewalDate = licenceRenewalDate;
    }
}
