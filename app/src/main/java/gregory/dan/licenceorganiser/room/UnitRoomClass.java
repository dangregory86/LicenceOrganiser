package gregory.dan.licenceorganiser.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Daniel Gregory on 30/08/2018.
 */
@Entity(tableName = "unit_table")
public class UnitRoomClass {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="unit_title")
    private String unitTitle;

    @NonNull
    @ColumnInfo(name="unit_address")
    private String unitAddress;

    @NonNull
    @ColumnInfo(name="unit_co")
    private String unitCO;

    @ColumnInfo(name="unit_licences")
    private String licences;

    @ColumnInfo(name="unit_points")
    private String points;

    @NonNull
    @ColumnInfo(name="unit_contact_number")
    private String unitContactNumber;

    @NonNull
    @ColumnInfo(name = "unit_next_inspection_date")
    private long nextInspectionDue;

    @ColumnInfo(name = "unit_last_inspection_date")
    private long lastInspection;

    @NonNull
    @ColumnInfo(name = "unit_table_updated_date")
    private long lastUpdated;

    @NonNull
    public String getUnitTitle() {
        return unitTitle;
    }

    public void setUnitTitle(@NonNull String unitTitle) {
        this.unitTitle = unitTitle;
    }

    @NonNull
    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(@NonNull String unitAddress) {
        this.unitAddress = unitAddress;
    }

    @NonNull
    public String getUnitCO() {
        return unitCO;
    }

    public void setUnitCO(@NonNull String unitCO) {
        this.unitCO = unitCO;
    }

    public String getLicences() {
        return licences;
    }

    public void setLicences(String licences) {
        this.licences = licences;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    @NonNull
    public String getUnitContactNumber() {
        return unitContactNumber;
    }

    public void setUnitContactNumber(@NonNull String unitContactNumber) {
        this.unitContactNumber = unitContactNumber;
    }

    @NonNull
    public long getNextInspectionDue() {
        return nextInspectionDue;
    }

    public void setNextInspectionDue(@NonNull long nextInspectionDue) {
        this.nextInspectionDue = nextInspectionDue;
    }

    public long getLastInspection() {
        return lastInspection;
    }

    public void setLastInspection(long lastInspection) {
        this.lastInspection = lastInspection;
    }

    @NonNull
    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(@NonNull long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
