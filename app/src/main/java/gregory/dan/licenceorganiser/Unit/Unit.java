package gregory.dan.licenceorganiser.Unit;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Daniel Gregory on 26/08/2018.
 */
@Entity(indices = {@Index("unitTitle")})
public class Unit{

    @PrimaryKey
    @NonNull
    public String unitTitle;

    public String unitAddress;

    public String unitContactNumber;

    public String unitCO;

    public Unit(){

    }

    public Unit(@NonNull String unitTitle, String unitAddress, String unitContactNumber, String unitCO) {
        this.unitTitle = unitTitle;
        this.unitAddress = unitAddress;
        this.unitContactNumber = unitContactNumber;
        this.unitCO = unitCO;
    }
}
