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
                entity = Licence.class,
                parentColumns = "licenceSerial",
                childColumns = "licence_serial")
}, indices = @Index("licence_serial"))
public class Ammunition {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "licence_serial")
    public String licenceSerial;

    public String adac;
    public String description;
    public String HCC;
    public int quantity;

    public Ammunition(String licenceSerial, String adac, String description, String HCC, int quantity) {
        this.licenceSerial = licenceSerial;
        this.adac = adac;
        this.description = description;
        this.HCC = HCC;
        this.quantity = quantity;
    }
}
