package gregory.dan.licenceorganiser.Unit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Daniel Gregory on 26/08/2018.
 */
public class Unit implements Parcelable{

    private String unitTitle, unitAddress, unitContactNumber, unitCO;
    private ArrayList<Licence> licences;
    private long lastInspection, nextInspectionDue;
    private ArrayList<Point> points;

    public Unit(String unitTitle) {
        this.unitTitle = unitTitle;
//        this.unitAddress = unitAddress;
//        this.unitContactNumber = unitContactNumber;
//        this.unitCO = unitCO;
//        this.licences = licences;
    }

    protected Unit(Parcel in) {
        unitTitle = in.readString();
        unitAddress = in.readString();
        unitContactNumber = in.readString();
        unitCO = in.readString();
        licences = in.createTypedArrayList(Licence.CREATOR);
        lastInspection = in.readLong();
        nextInspectionDue = in.readLong();
        points = in.createTypedArrayList(Point.CREATOR);
    }

    public static final Creator<Unit> CREATOR = new Creator<Unit>() {
        @Override
        public Unit createFromParcel(Parcel in) {
            return new Unit(in);
        }

        @Override
        public Unit[] newArray(int size) {
            return new Unit[size];
        }
    };

    public String getUnitTitle() {
        return unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public String getUnitContactNumber() {
        return unitContactNumber;
    }

    public void setUnitContactNumber(String unitContactNumber) {
        this.unitContactNumber = unitContactNumber;
    }

    public String getUnitCO() {
        return unitCO;
    }

    public void setUnitCO(String unitCO) {
        this.unitCO = unitCO;
    }

    public ArrayList<Licence> getLicences() {
        return licences;
    }

    public void setLicences(ArrayList<Licence> licences) {
        this.licences = licences;
    }

    public long getLastInspection() {
        return lastInspection;
    }

    public void setLastInspection(long lastInspection) {
        this.lastInspection = lastInspection;
    }

    public long getNextInspectionDue() {
        return nextInspectionDue;
    }

    public void setNextInspectionDue(long nextInspectionDue) {
        this.nextInspectionDue = nextInspectionDue;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(unitTitle);
        dest.writeString(unitAddress);
        dest.writeString(unitContactNumber);
        dest.writeString(unitCO);
        dest.writeTypedList(licences);
        dest.writeLong(lastInspection);
        dest.writeLong(nextInspectionDue);
        dest.writeTypedList(points);
    }
}
